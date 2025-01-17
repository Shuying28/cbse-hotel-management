package com.example.hotelmanagement.entity;

import com.example.hotelmanagement.enums.Enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "role_salary")
public class RoleSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role; // Role of the user ("Manager", "Employee", "Cleaner")

    @Column(name = "salary_percentage", nullable = false)
    private Double salaryPercentage; // Percentage of revenue allocated for this role

    @Column(name = "base_salary", nullable = false)
    private Double baseSalary; // Base salary for the role

    public RoleSalary() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Double getSalaryPercentage() {
        return salaryPercentage;
    }

    public void setSalaryPercentage(Double salaryPercentage) {
        this.salaryPercentage = salaryPercentage;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }
}
