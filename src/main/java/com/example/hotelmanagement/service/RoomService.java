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
     * @param roomNumber The unique room number (e.g., "A-101").
     * @param price      The price per night for the room.
     * @param status     The initial status of the room (e.g., Available, Reserved, etc.).
     * @param type       The type of the room (e.g., Single, Double, Suite).
     * @return The added room.
     */
    Room addRoom(String roomNumber, double price, RoomStatus status, RoomType type);

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
     * @param roomNumber The updated room number.
     * @param price      The updated price per night for the room.
     * @param status     The updated status of the room.
     * @param type       The updated type of the room (e.g., Single, Double, Suite).
     * @return The updated room, or null if the room was not found.
     */
    Room updateRoom(Long roomId, String roomNumber, double price, RoomStatus status, RoomType type);

    /**
     * Update the status of a room.
     *
     * @param roomId The ID of the room.
     * @param status The new status of the room (e.g., Available, Reserved, etc.).
     * @return The updated room, or null if the room was not found.
     */
    Room updateRoomStatus(Long roomId, RoomStatus status);

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
