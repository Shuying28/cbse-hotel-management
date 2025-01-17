package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.enums.Enums.RoomStatus;
import com.example.hotelmanagement.enums.Enums.RoomType;
import com.example.hotelmanagement.repository.RoomRepository;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    @Override
    public Room addRoom(String roomNumber, double price, RoomStatus status, RoomType type) {
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomPrice(price);
        room.setRoomStatus(status);
        room.setRoomType(type);
        return roomRepository.save(room);
    }

    @Override
    public boolean deleteRoom(Long roomId) {
        if (roomRepository.existsById(roomId)) {
            roomRepository.deleteById(roomId);
            return true;
        }
        return false;
    }

    @Override
    public Room updateRoom(Long roomId, String roomNumber, double price, RoomStatus status, RoomType type) {
        return roomRepository.findById(roomId).map(room -> {
            if (roomNumber != null && !roomNumber.equals("null")) {
                room.setRoomNumber(roomNumber);
            }
            if (price >= 0) {
                room.setRoomPrice(price);
            }
            if (status != null) {
                room.setRoomStatus(status);
            }
            if (type != null) {
                room.setRoomType(type);
            }
            return roomRepository.save(room);
        }).orElse(null);
    }

    @Override
    public Room updateRoomStatus(Long roomId, RoomStatus status) {
        return roomRepository.findById(roomId).map(room -> {
            room.setRoomStatus(status);
            return roomRepository.save(room);
        }).orElse(null);
    }

    @Override
    public List<Room> getRoomsByStatus(RoomStatus status, RoomType type) {
        if (status != null && type != null) {
            return roomRepository.findByRoomStatusAndRoomType(status, type);
        } else if (status != null) {
            return roomRepository.findByRoomStatus(status);
        } else if (type != null) {
            return roomRepository.findByRoomType(type);
        } else {
            return roomRepository.findAll();
        }
    }

    @Override
    public List<Room> searchRoom(String keyword) {
        RoomType roomType = null;

        // Attempt to parse keyword as RoomType
        try {
            roomType = RoomType.valueOf(keyword.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Not a valid RoomType, so roomType remains null
        }

        return roomRepository.findByRoomNumberContainingIgnoreCaseOrRoomType(keyword, roomType);
    }
}
