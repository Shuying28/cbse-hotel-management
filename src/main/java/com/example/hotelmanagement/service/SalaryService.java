package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.RoleSalary;
import com.example.hotelmanagement.entity.Salary;
import com.example.hotelmanagement.enums.Enums.UserRole;

import java.util.List;

public interface SalaryService {

    /**
     * Get all salary configurations.
     *
     * @return A list of Salary objects.
     */
    List<Salary> getSalary();

    /**
     * Get all role salary configurations.
     *
     * @return A list of RoleSalary objects.
     */
    List<RoleSalary> getAllRoleSalary();

    /**
     * Get role salaries by UserRole.
     *
     * @param role The UserRole to search for.
     * @return A list of RoleSalary objects for the specified role.
     */
    List<RoleSalary> getRoleSalaryByRole(UserRole role);

    /**
     * Update an existing role salary.
     *
     * @param roleSalaryId     The ID of the RoleSalary to update.
     * @param baseSalary       The updated base salary.
     * @param salaryPercentage The updated salary percentage.
     * @return The updated RoleSalary object.
     */
    RoleSalary updateRoleSalary(Long roleSalaryId, Double baseSalary, Double salaryPercentage);

    /**
     * Update a salary configuration.
     *
     * @param salaryId      The ID of the Salary to update.
     * @param totalRevenue  The updated total revenue.
     * @param totalBookings The updated total bookings.
     * @return The updated Salary object.
     */
    Salary updateSalary(Long salaryId, Double totalRevenue, int totalBookings);

    /**
     * Add a new RoleSalary to an existing Salary.
     *
     * @param salaryId         The ID of the Salary to add the RoleSalary to.
     * @param role             The UserRole for the new RoleSalary.
     * @param baseSalary       The base salary for the role.
     * @param salaryPercentage The salary percentage for the role.
     * @return The updated Salary object.
     */
    Salary addRoleSalary(Long salaryId, UserRole role, Double baseSalary, Double salaryPercentage);

    /**
     * Delete a RoleSalary by ID.
     *
     * @param roleSalaryId The ID of the RoleSalary to delete.
     * @return true if the RoleSalary was successfully deleted, false otherwise.
     */
    boolean deleteRoleSalary(Long roleSalaryId);

    /**
     * Search for role salaries by a keyword.
     *
     * @param keyword The keyword to search for in role, base salary, or salary percentage.
     * @return A list of RoleSalary objects matching the keyword.
     */
    List<RoleSalary> searchRoleSalary(String keyword);
}
