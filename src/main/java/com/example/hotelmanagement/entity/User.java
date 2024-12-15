package com.example.hotelmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Order ID

    @Column(name = "room_id", nullable = false)
    private Long roomId; // Associated Room ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Associated User

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime; // Reservation start time

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime; // Reservation end time

    @Column(name = "total_price", nullable = false)
    private double totalPrice; // Total price for the reservation

    @Column(name = "current_status", nullable = false)
    private String currentStatus; // Order status (e.g., "Paid", "Unpaid", "Checked-In", "Not Checked-In", "Cancelled")

    // Default constructor
    public Order() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
