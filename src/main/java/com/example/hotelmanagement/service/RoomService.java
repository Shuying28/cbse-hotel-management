package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Course;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.enums.Enums.RoomType;
import com.example.hotelmanagement.enums.Enums.RoomStatus;

import java.util.List;

public interface RoomService {
    /**
     * Retrieve room details by room ID.
     *
     * @param roomId The ID of the room.
     * @return The room object corresponding to the given ID, or null if not found.
     */
    Room getRoomById(Long roomId);

    /**
     * Add a new room.
     *
     * @param room The room object containing details such as roomNumber, roomPrice, etc...
     * @return The added room.
     */
    Room addRoom(Room room);

    /**
     * Delete a room by its ID.
     *
     * @param roomId The ID of the room to delete.
     * @return true if the room was successfully deleted, false otherwise.
     */
    boolean deleteRoom(Long roomId);

    /**
     * Update the details of an existing room.
     *
     * @param roomId     The ID of the room to update.
     * @param room The updated room object.
     * @return The updated room, or null if the room was not found.
     */
    Room updateRoom(Long roomId, Room room);

    /**
     * Retrieve rooms filtered by their status and/or type.
     *
     * @param status The status of the rooms to retrieve (optional).
     * @param type   The type of the rooms to retrieve (optional).
     * @return A list of rooms matching the specified criteria.
     */
    List<Room> getRoomsByStatus(RoomStatus status, RoomType type);

    /**
     * Search for rooms by a keyword.
     *
     * @param keyword The keyword to search for in room numbers or types.
     * @return A list of rooms matching the keyword.
     */
    List<Room> searchRoom(String keyword);
}
