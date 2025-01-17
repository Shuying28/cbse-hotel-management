package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Reservation;
import com.example.hotelmanagement.enums.Enums.ReservationStatus;
import com.example.hotelmanagement.enums.Enums.PaymentStatus;

import java.util.List;

public interface ReservationService {

    /**
     * Add a new reservation.
     *
     * @param reservation The reservation object to be added.
     * @return The added reservation.
     */
    Reservation addReservation(Reservation reservation);

    /**
     * Delete a reservation.
     *
     * @param reservationId The ID of the reservation to delete.
     * @return true if the reservation is successfully deleted, false otherwise.
     */
    boolean deleteReservation(Long reservationId);

    /**
     * Update the current status of a reservation.
     *
     * @param reservationId    The ID of the reservation to update.
     * @param reservationStatus The new status of the reservation (e.g., "Checked-In", "Cancelled").
     *                          See {@link ReservationStatus} for a list of possible statuses.
     * @param paymentStatus    The new payment status of the reservation (e.g., "Paid", "Unpaid").
     *                          See {@link PaymentStatus} for a list of possible statuses.
     * @return The updated reservation.
     */
    Reservation updateReservationStatus(Long reservationId, ReservationStatus reservationStatus, PaymentStatus paymentStatus);

    /**
     * Update an existing reservation.
     *
     * @param reservationId The ID of the reservation to update.
     * @param reservation The reservation object to be updated.
     * @return The updated reservation.
     */
    Reservation updateReservation(Long reservationId, Reservation reservation);

    /**
     * Retrieve all reservations.
     *
     * @return A list of all reservations.
     */
    List<Reservation> getAllReservations();

    /**
     * Retrieve a reservation by its ID.
     *
     * @param reservationId The ID of the reservation to retrieve.
     * @return The reservation with the specified ID, or null if not found.
     */
    Reservation getReservation(Long reservationId);

    /**
     * Search reservations by keyword in household name or user name (case insensitive).
     *
     * @param keyword the keyword to search for in household name or user name
     * @return A list of reservations matching the keyword.
     */
    List<Reservation> searchReservation(String keyword);
}
