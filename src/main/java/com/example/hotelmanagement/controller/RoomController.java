package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.enums.Enums.RoomStatus;
import com.example.hotelmanagement.enums.Enums.RoomType;
import com.example.hotelmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

	private final RoomService roomService;

	@Autowired
	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}

	// Get room details by room ID.
	@GetMapping("/{id}")
	public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
		Room room = roomService.getRoomById(id);
		return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
	}

	// Add a new room.
	@PostMapping
	public ResponseEntity<Room> addRoom(@RequestBody Room room) {
		Room createdRoom = roomService.addRoom(room);
		return ResponseEntity.ok(createdRoom);
	}

	// Delete a room by its ID.
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteRoom(@PathVariable Long id) {
		boolean deleted = roomService.deleteRoom(id);
		return deleted ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
	}

	// Update the details of an existing room.
	@PutMapping("/{id}")
	public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
		Room updatedRoom = roomService.updateRoom(id, room);
		return updatedRoom != null ? ResponseEntity.ok(updatedRoom) : ResponseEntity.notFound().build();
	}

	// Retrieve rooms filtered by their status and/or type.
	@GetMapping("/filter")
	public ResponseEntity<List<Room>> getRoomsByStatus(
			@RequestParam(required = false) RoomStatus status,
			@RequestParam(required = false) RoomType type
	) {
		List<Room> rooms = roomService.getRoomsByStatus(status, type);
		return ResponseEntity.ok(rooms);
	}

	// Search for rooms by a keyword.
	@GetMapping("/search")
	public ResponseEntity<List<Room>> searchRoom(@RequestParam String keyword) {
		List<Room> rooms = roomService.searchRoom(keyword);
		return ResponseEntity.ok(rooms);
	}
}
