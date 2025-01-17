package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.RoleSalary;
import com.example.hotelmanagement.entity.Salary;
import com.example.hotelmanagement.enums.Enums.UserRole;
import com.example.hotelmanagement.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

	private final SalaryService salaryService;

	@Autowired
	public SalaryController(SalaryService salaryService) {
		this.salaryService = salaryService;
	}

	// Get salary configurations.
	@GetMapping
	public ResponseEntity<List<Salary>> getSalary() {
		return ResponseEntity.ok(salaryService.getSalary());
	}

	// Get all role salary configurations.
	@GetMapping("/roles")
	public ResponseEntity<List<RoleSalary>> getAllRoleSalaries() {
		return ResponseEntity.ok(salaryService.getAllRoleSalary());
	}

	// Get role salaries by UserRole.
	@GetMapping("/roles/{role}")
	public ResponseEntity<RoleSalary> getRoleSalaryByRole(@PathVariable UserRole role) {
		return ResponseEntity.ok(salaryService.getRoleSalaryByRole(role));
	}

	// Update an existing role salary.
	@PutMapping("/roles/{id}")
	public ResponseEntity<RoleSalary> updateRoleSalary(
			@PathVariable Long id,
			@RequestBody RoleSalary roleSalary
	) {
		RoleSalary updatedRoleSalary = salaryService.updateRoleSalary(id, roleSalary);
		return ResponseEntity.ok(updatedRoleSalary);
	}

	// Add a new salary configuration.
	@PostMapping
	public ResponseEntity<Salary> addSalary(@RequestBody Salary salary) {
		Salary createdSalary = salaryService.addSalary(salary);
		return ResponseEntity.ok(createdSalary);
	}

	// Update a salary configuration.
	@PutMapping("/{id}")
	public ResponseEntity<Salary> updateSalary(
			@PathVariable Long id,
			@RequestParam Double totalRevenue,
			@RequestParam int totalBookings
	) {
		Salary updatedSalary = salaryService.updateSalary(id, totalRevenue, totalBookings);
		return ResponseEntity.ok(updatedSalary);
	}

	// Add a new RoleSalary to an existing Salary.
	@PostMapping("/{id}/roles")
	public ResponseEntity<Salary> addRoleSalary(
			@PathVariable Long id,
			@RequestParam UserRole role,
			@RequestParam Double baseSalary,
			@RequestParam Double salaryPercentage
	) {
		Salary updatedSalary = salaryService.addRoleSalary(id, role, baseSalary, salaryPercentage);
		return ResponseEntity.ok(updatedSalary);
	}

	// Delete a RoleSalary by ID.
	@DeleteMapping("/roles/{id}")
	public ResponseEntity<Boolean> deleteRoleSalary(@PathVariable Long id) {
		boolean deleted = salaryService.deleteRoleSalary(id);
		return deleted ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
	}

	// Search for role salaries by a keyword.
	@GetMapping("/roles/search")
	public ResponseEntity<List<RoleSalary>> searchRoleSalary(@RequestParam String keyword) {
		return ResponseEntity.ok(salaryService.searchRoleSalary(keyword));
	}
}
