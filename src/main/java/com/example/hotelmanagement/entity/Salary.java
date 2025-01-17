package com.example.hotelmanagement.entity;

import com.example.hotelmanagement.enums.Enums.UserRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_revenue", nullable = false)
    private Double totalRevenue; // Total revenue generated by the hotel

    @Column(name = "total_bookings", nullable = false)
    private int totalBookings; // Total number of bookings made

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "salary_id")
    private List<RoleSalary> roleSalaries; // List of roles with their respective base salary and salary percentage

    public Salary() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public void incrementTotalRevenue(Double revenue) {
        this.totalRevenue += revenue;
    }

    public void incrementTotalBookings() {
        this.totalBookings += 1;
    }

    public List<RoleSalary> getRoleSalaries() {
        return roleSalaries;
    }

    public void setRoleSalaries(List<RoleSalary> roleSalaries) {
        this.roleSalaries = roleSalaries;
    }
}
