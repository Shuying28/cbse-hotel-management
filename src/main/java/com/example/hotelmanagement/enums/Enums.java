package com.example.hotelmanagement.enums;

public class Enums {

    // Room Status Enum
    public enum RoomStatus {
        DEACTIVATED("Deactivated"),
        AVAILABLE("Available"),
        RESERVED("Reserved"),
        PENDING_CLEANING("Pending Cleaning");

        private final String status;

        RoomStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    // Room Type Enum
    public enum RoomType {
        SINGLE("Single"),
        DOUBLE("Double"),
        SUITE("Suite");

        private final String type;

        RoomType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    // Reservation Status Enum
    public enum ReservationStatus {
        CHECKED_IN("Checked-In"),
        NOT_CHECKED_IN("Not Checked-In"),
        CHECKED_OUT("Checked-Out"),
        CANCELLED("Cancelled");

        private final String status;

        ReservationStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    // User Role Enum
    public enum UserRole {
        MANAGER("Manager"),
        EMPLOYEE("Employee"),
        CLEANER("Cleaner"),
        CUSTOMER("Customer");

        private final String role;

        UserRole(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    // Payment Method Enum
    public enum PaymentStatus {
        PAID("Paid"),
        UNPAID("Unpaid");

        private final String status;

        PaymentStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
