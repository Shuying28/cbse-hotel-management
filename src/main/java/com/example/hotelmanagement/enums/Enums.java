package com.example.hotelmanagement.enums;

public class Enums {

    // Room Status Enum
    public enum RoomStatus {
        DEACTIVATED,
        AVAILABLE,
        RESERVED,
        PENDING_CLEANING;
    }

    // Room Type Enum
    public enum RoomType {
        SINGLE,
        DOUBLE,
        SUITE;
    }

    // Reservation Status Enum
    public enum ReservationStatus {
        CHECKED_IN,
        NOT_CHECKED_IN,
        CHECKED_OUT,
        CANCELLED;
    }

    // User Role Enum
    public enum UserRole {
        MANAGER,
        EMPLOYEE,
        CLEANER,
        CUSTOMER;
    }

    // Payment Method Enum
    public enum PaymentStatus {
        PAID,
        UNPAID;
    }
}
