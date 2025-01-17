package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Derived Query Method
    List<Reservation> findByHouseholdNameContainingIgnoreCaseOrUser_UsernameContainingIgnoreCase(String householdName, String userName);
}