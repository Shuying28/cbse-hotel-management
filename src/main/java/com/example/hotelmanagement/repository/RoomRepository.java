package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.enums.Enums.RoomStatus;
import com.example.hotelmanagement.enums.Enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomStatusAndRoomType(RoomStatus status, RoomType type);

    List<Room> findByRoomStatus(RoomStatus status);

    List<Room> findByRoomType(RoomType type);

    List<Room> findByRoomNumberContainingIgnoreCaseOrRoomType(String roomNumber, RoomType roomType);
}