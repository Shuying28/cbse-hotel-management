package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.RoleSalary;
import com.example.hotelmanagement.entity.Salary;
import com.example.hotelmanagement.enums.Enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleSalaryRepository extends JpaRepository<RoleSalary, Long> {
    List<RoleSalary> findByRole(UserRole role);

    List<RoleSalary> findByRoleOrBaseSalaryOrSalaryPercentage(UserRole role, Double baseSalary, Double salaryPercentage);
}