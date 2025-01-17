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
    public Room addRoom(Room room) {
        Room createdRoom = new Room();
        createdRoom.setRoomNumber(room.getRoomNumber());
        createdRoom.setRoomPrice(room.getRoomPrice());
        createdRoom.setRoomStatus(RoomStatus.AVAILABLE);
        createdRoom.setRoomType(room.getRoomType());
        // Generates a number between 0000 and 9999
        String roomPassword = String.format("%04d", (int) (Math.random() * 10000));
        createdRoom.setRoomPassword(roomPassword);
        createdRoom.setTargetOccupancy(room.getTargetOccupancy());
        return roomRepository.save(createdRoom);
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
    public Room updateRoom(Long roomId, Room room) {
        return roomRepository.findById(roomId).map(existingRoom -> {
            if (room.getRoomNumber() != null && !room.getRoomNumber().equalsIgnoreCase("null")) {
                existingRoom.setRoomNumber(room.getRoomNumber());
            }
            if (room.getRoomPrice() > 0) {
                existingRoom.setRoomPrice(room.getRoomPrice());
            }
            if (room.getRoomStatus() != null) {
                existingRoom.setRoomStatus(room.getRoomStatus());
            }
            if (room.getRoomPassword() != null) {
                existingRoom.setRoomPassword(room.getRoomPassword());
            }
            if (room.getTargetOccupancy() > 0) {
                existingRoom.setTargetOccupancy(room.getTargetOccupancy());
            }
            if (room.getRoomType() != null) {
                existingRoom.setRoomType(room.getRoomType());
            }
            return roomRepository.save(existingRoom);
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
