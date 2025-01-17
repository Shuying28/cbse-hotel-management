package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.RoleSalary;
import com.example.hotelmanagement.entity.Salary;
import com.example.hotelmanagement.enums.Enums.UserRole;
import com.example.hotelmanagement.repository.RoleSalaryRepository;
import com.example.hotelmanagement.repository.SalaryRepository;
import com.example.hotelmanagement.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private RoleSalaryRepository roleSalaryRepository;

    @Override
    public List<Salary> getSalary() {
        return salaryRepository.findAll();
    }

    @Override
    public List<RoleSalary> getAllRoleSalary() {
        return roleSalaryRepository.findAll();
    }

    @Override
    public List<RoleSalary> getRoleSalaryByRole(UserRole role) {
        return roleSalaryRepository.findByRole(role);
    }

    @Override
    public RoleSalary updateRoleSalary(Long roleSalaryId, Double baseSalary, Double salaryPercentage) {
        Optional<RoleSalary> roleSalaryOptional = roleSalaryRepository.findById(roleSalaryId);
        if (roleSalaryOptional.isPresent()) {
            RoleSalary roleSalary = roleSalaryOptional.get();
            if (baseSalary != null) {
                roleSalary.setBaseSalary(baseSalary);
            }
            if (salaryPercentage != null) {
                roleSalary.setSalaryPercentage(salaryPercentage);
            }
            return roleSalaryRepository.save(roleSalary);
        }
        throw new RuntimeException("RoleSalary not found for ID: " + roleSalaryId);
    }

    @Override
    public Salary updateSalary(Long salaryId, Double totalRevenue, int totalBookings) {
        Optional<Salary> salaryOptional = salaryRepository.findById(salaryId);
        if (salaryOptional.isPresent()) {
            Salary salary = salaryOptional.get();
            if (totalRevenue != null) {
                salary.setTotalRevenue(totalRevenue);
            }
            if (totalBookings >= 0) {
                salary.setTotalBookings(totalBookings);
            }
            return salaryRepository.save(salary);
        }
        throw new RuntimeException("Salary configuration not found for ID: " + salaryId);
    }

    @Override
    public Salary addRoleSalary(Long salaryId, UserRole role, Double baseSalary, Double salaryPercentage) {
        Optional<Salary> salaryOptional = salaryRepository.findById(salaryId);
        if (salaryOptional.isPresent()) {
            Salary salary = salaryOptional.get();

            RoleSalary roleSalary = new RoleSalary();
            roleSalary.setRole(role);
            roleSalary.setBaseSalary(baseSalary);
            roleSalary.setSalaryPercentage(salaryPercentage);

            roleSalaryRepository.save(roleSalary);

            salary.getRoleSalaries().add(roleSalary);
            return salaryRepository.save(salary);
        }
        throw new RuntimeException("Salary configuration not found for ID: " + salaryId);
    }

    @Override
    public boolean deleteRoleSalary(Long roleSalaryId) {
        if (roleSalaryRepository.existsById(roleSalaryId)) {
            roleSalaryRepository.deleteById(roleSalaryId);
            return true;
        }
        return false;
    }

    @Override
    public List<RoleSalary> searchRoleSalary(String keyword) {
        UserRole role = null;
        Double baseSalary = null;
        Double salaryPercentage = null;

        // Attempt to parse keyword as UserRole
        try {
            role = UserRole.valueOf(keyword.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            // Not a valid UserRole, move on to parse as numbers
        }

        // Attempt to parse keyword as Double
        try {
            baseSalary = Double.parseDouble(keyword);
            salaryPercentage = Double.parseDouble(keyword);
        } catch (NumberFormatException ignored) {
            // Not a valid Double, move on
        }

        return roleSalaryRepository.findByRoleOrBaseSalaryOrSalaryPercentage(role, baseSalary, salaryPercentage);
    }
}
