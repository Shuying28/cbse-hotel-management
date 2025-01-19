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
    public Reservation addReservation(Reservation reservation) {
        List<Room> rooms = roomRepository.findAllById(
                reservation.getRooms().stream().map(Room::getId).toList()
        );
        if (rooms.isEmpty() || rooms.stream().anyMatch(room -> room.getRoomStatus() != RoomStatus.AVAILABLE)) {
            throw new RuntimeException("One or more rooms are not available.");
        }

        // Validate and fetch user, if present
        if (reservation.getUser() != null && reservation.getUser().getId() != null) {
            User user = userRepository.findById(reservation.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            reservation.setUser(user);
        }

        reservation.setHouseholdName(reservation.getHouseholdName());
        reservation.setRooms(rooms);
        reservation.setStartTime(reservation.getStartTime());
        reservation.setEndTime(reservation.getEndTime());
        reservation.setReservationStatus(ReservationStatus.NOT_CHECKED_IN);
        reservation.setPaymentStatus(PaymentStatus.UNPAID);

        double totalPrice = TimeUtil.getBetweenDay(reservation.getStartTime(), reservation.getEndTime()) *
                rooms.stream().mapToDouble(Room::getRoomPrice).sum();
        reservation.setTotalPrice(totalPrice);

        Salary salary = salaryRepository.findById(3L).orElse(new Salary());
        salary.incrementTotalBookings();
        salary.incrementTotalRevenue(totalPrice);
        salaryRepository.save(salary);

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
                reservation.setPaymentStatus(paymentStatus);
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
    public Reservation updateReservation(Long reservationId, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));


        // Update rooms if provided
        if (reservation.getRooms() != null && !reservation.getRooms().isEmpty()) {
            List<Room> rooms = roomRepository.findAllById(
                    reservation.getRooms().stream().map(Room::getId).toList()
            );
            if (rooms.isEmpty() || rooms.stream().anyMatch(room -> room.getRoomStatus() != RoomStatus.AVAILABLE)) {
                throw new RuntimeException("One or more rooms are not available.");
            }
            existingReservation.getRooms().forEach(room -> {
                room.setRoomStatus(RoomStatus.AVAILABLE);
            });
            existingReservation.setRooms(rooms);

            rooms.forEach(room -> {
                room.setRoomStatus(RoomStatus.RESERVED);
                roomRepository.save(room);
            });
        }

        if (reservation.getUser() != null && reservation.getUser().getId() != null) {
            User user = userRepository.findById(reservation.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingReservation.setUser(user);
        }

        if (reservation.getHouseholdName() != null) {
            existingReservation.setHouseholdName(reservation.getHouseholdName());
        }

        if (reservation.getStartTime() != null) {
            existingReservation.setStartTime(reservation.getStartTime());
        }

        if (reservation.getEndTime() != null) {
            existingReservation.setEndTime(reservation.getEndTime());
        }

        // Recalculate total price if startTime, endTime, or rooms have changed
        if (reservation.getStartTime() != null || reservation.getEndTime() != null || reservation.getRooms() != null) {
            double totalPrice = TimeUtil.getBetweenDay(
                    existingReservation.getStartTime(),
                    existingReservation.getEndTime()
            ) * existingReservation.getRooms().stream().mapToDouble(Room::getRoomPrice).sum();
            existingReservation.setTotalPrice(totalPrice);
        }

        return reservationRepository.save(existingReservation);
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
