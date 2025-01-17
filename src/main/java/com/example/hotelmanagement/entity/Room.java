package com.example.hotelmanagement.entity;

import com.example.hotelmanagement.enums.Enums.RoomStatus;
import com.example.hotelmanagement.enums.Enums.RoomType;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber; // A-111, B-222, etc.

    @Column(name = "room_price", nullable = false)
    private double roomPrice; // Price per night

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status", nullable = false)
    private RoomStatus roomStatus; // "Deactivated", "Available", "Reserved", "Pending Cleaning"

    @Column(name = "room_password")
    private String roomPassword; // Password for accessing the room for digital locks

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType; // Single, Double, Suite

    @Column(name = "target_occupancy", nullable = false)
    private int targetOccupancy; // Maximum occupancy

    /**
     *  The relationship is mapped and owned by the rooms field in the Reservation entity.
     *  Changes to rooms in the Reservation entity control the relationship.
     */
    @ManyToMany(mappedBy = "rooms")
    private List<Reservation> reservations; // Reservations associated with the room

    public Room() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getTargetOccupancy() {
        return targetOccupancy;
    }

    public void setTargetOccupancy(int targetOccupancy) {
        this.targetOccupancy = targetOccupancy;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
