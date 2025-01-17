package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Salary;
import com.example.hotelmanagement.entity.Reservation;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.enums.Enums.RoomStatus;
import com.example.hotelmanagement.enums.Enums.ReservationStatus;
import com.example.hotelmanagement.enums.Enums.PaymentStatus;
import com.example.hotelmanagement.repository.SalaryRepository;
import com.example.hotelmanagement.repository.ReservationRepository;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.repository.UserRepository;
import com.example.hotelmanagement.service.ReservationService;
import com.example.hotelmanagement.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Reservation addReservation(String householdName, String startTime, String endTime, List<Long> roomIds, Long userId) {
        // get list here
        List<Room> rooms = roomRepository.findAllById(roomIds);
        if (rooms.isEmpty() || rooms.stream().anyMatch(room -> room.getRoomStatus() != RoomStatus.AVAILABLE)) {
            throw new RuntimeException("One or more rooms are not available.");
        }


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reservation reservation = new Reservation();
        reservation.setHouseholdName(householdName);
        reservation.setStartTime(TimeUtil.formatterTime(startTime));
        reservation.setEndTime(TimeUtil.formatterTime(endTime));
        reservation.setRooms(rooms);
        reservation.setUser(user);
        reservation.setReservationStatus(ReservationStatus.NOT_CHECKED_IN);
        reservation.setPaymentStatus(PaymentStatus.UNPAID);

        double totalPrice = TimeUtil.getBetweenDay(startTime, endTime) * rooms.stream().mapToDouble(Room::getRoomPrice).sum();
        reservation.setTotalPrice(totalPrice);

        // Update Salary
        Salary salary = salaryRepository.findById(1L).orElse(new Salary());
        salary.setTotalBookings(salary.getTotalBookings() + 1);
        salary.setTotalRevenue(salary.getTotalRevenue() + totalPrice);
        salaryRepository.save(salary);

        // Update room state
        rooms.forEach(room -> {
            room.setRoomStatus(RoomStatus.RESERVED);
            roomRepository.save(room);
        });

        return reservationRepository.save(reservation);
    }

    @Override
    public boolean deleteReservation(Long reservationid) {
        Reservation reservation = reservationRepository.findById(reservationid).orElse(null);
        if (reservation == null) {
            return false;
        }

        for (Room room : reservation.getRooms()) {
            room.setRoomStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);
        }

        reservationRepository.deleteById(reservationid);
        return true;
    }

    @Override
    public Reservation updateReservationStatus(Long reservationId, ReservationStatus reservationStatus, PaymentStatus paymentStatus) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation == null) {
            throw new RuntimeException("Reservation not found.");
        }

        // Update status for all associated rooms
        for (Room room : reservation.getRooms()) {
            if (paymentStatus == PaymentStatus.PAID) {
                room.setRoomStatus(RoomStatus.PENDING_CLEANING);
            } else if (reservationStatus == ReservationStatus.CANCELLED) {
                room.setRoomStatus(RoomStatus.AVAILABLE);
            }
            roomRepository.save(room);
        }

        reservation.setReservationStatus(reservationStatus);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Long reservationId, String householdName, String startTime, String endTime, List<Long> roomIds, Long userId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation == null) {
            throw new RuntimeException("Reservation not found.");
        }

        List<Room> rooms = roomRepository.findAllById(roomIds);
        if (rooms.isEmpty() || rooms.stream().anyMatch(room -> room.getRoomStatus() != RoomStatus.AVAILABLE)) {
            throw new RuntimeException("One or more rooms are not available.");
        }
        rooms.forEach(room -> {
            room.setRoomStatus(RoomStatus.RESERVED);
            roomRepository.save(room);
        });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        reservation.setHouseholdName(householdName);
        reservation.setStartTime(TimeUtil.formatterTime(startTime));
        reservation.setEndTime(TimeUtil.formatterTime(endTime));
        reservation.setRooms(rooms);
        reservation.setUser(user);

        double totalPrice = TimeUtil.getBetweenDay(startTime, endTime) * rooms.stream().mapToDouble(Room::getRoomPrice).sum();
        reservation.setTotalPrice(totalPrice);

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    @Override
    public List<Reservation> searchReservation(String keyword) {
        return reservationRepository.findByHouseholdNameContainingIgnoreCaseOrUser_UsernameContainingIgnoreCase(keyword, keyword);
    }

    // TODO process payment
}
