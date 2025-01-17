package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.enums.Enums.UserRole;
import com.example.hotelmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// Retrieve user details by user ID.
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}

	// Add a new user.
	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User createdUser = userService.addUser(user);
		return ResponseEntity.ok(createdUser);
	}

	// Delete a user by user ID.
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
		boolean deleted = userService.deleteUser(id);
		return deleted ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
	}

	// Update user information.
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updatedUser = userService.updateUser(id, user);
		return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
	}

	// Update user performance bonus.
	@PatchMapping("/{id}/bonus")
	public ResponseEntity<User> updatePerformanceBonus(@PathVariable Long id, @RequestParam double bonus) {
		User updatedUser = userService.updatePerformanceBonus(id, bonus);
		return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
	}

	// Retrieve all users.
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	// Retrieve users based on their role.
	@GetMapping("/role/{role}")
	public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRole role) {
		return ResponseEntity.ok(userService.getUsersByRole(role));
	}

	// Validate user login credentials.
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
		User user = userService.login(username, password);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}

	// Search for users by a keyword.
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUser(@RequestParam String keyword) {
		return ResponseEntity.ok(userService.searchUser(keyword));
	}
}
