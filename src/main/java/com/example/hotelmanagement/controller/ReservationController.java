package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.Reservation;
import com.example.hotelmanagement.enums.Enums.ReservationStatus;
import com.example.hotelmanagement.enums.Enums.PaymentStatus;
import com.example.hotelmanagement.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	@Autowired
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	// Add a new reservation.
	@PostMapping
	public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
		Reservation createdReservation = reservationService.addReservation(reservation);
		return ResponseEntity.ok(createdReservation);
	}

	// Delete a reservation by ID.
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteReservation(@PathVariable Long id) {
		boolean deleted = reservationService.deleteReservation(id);
		return deleted ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
	}

	// Update the status of a reservation.
	@PatchMapping("/{id}/status")
	public ResponseEntity<Reservation> updateReservationStatus(
			@PathVariable Long id,
			@RequestParam ReservationStatus status,
			@RequestParam(required = false) PaymentStatus paymentStatus
	) {
		Reservation updatedReservation = reservationService.updateReservationStatus(id, status, paymentStatus);
		return ResponseEntity.ok(updatedReservation);
	}

	// Update an existing reservation.
	@PutMapping("/{id}")
	public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
		Reservation updatedReservation = reservationService.updateReservation(id, reservation);
		return ResponseEntity.ok(updatedReservation);
	}

	// Retrieve all reservations.
	@GetMapping
	public ResponseEntity<List<Reservation>> getAllReservations() {
		return ResponseEntity.ok(reservationService.getAllReservations());
	}

	// Retrieve a reservation by ID.
	@GetMapping("/{id}")
	public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
		Reservation reservation = reservationService.getReservation(id);
		return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
	}

	// Search for reservations by keyword.
	@GetMapping("/search")
	public ResponseEntity<List<Reservation>> searchReservations(@RequestParam String keyword) {
		return ResponseEntity.ok(reservationService.searchReservation(keyword));
	}
}
