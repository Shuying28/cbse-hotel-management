package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Course;
import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.enums.Enums.UserRole;

import java.util.List;
public interface UserService {
    /**
     * Retrieve user details by user ID.
     *
     * @param userId The ID of the user.
     * @return The User object, or null if no user is found with the given ID.
     */
    User getUserById(Long userId);

    /**
     * Add a new user.
     *
     * @param username The username for the user.
     * @param password The password for the user.
     * @param role     The role or permission level of the user.
     * @return The added User object.
     */
    User addUser(String username, String password, UserRole role);

    /**
     * Delete a user by user ID.
     *
     * @param userId The ID of the user to be deleted.
     * @return true if the user is deleted successfully, false otherwise.
     */
    boolean deleteUser(Long userId);

    /**
     * Update user information.
     *
     * @param userId      The ID of the user to be updated.
     * @param password    The new password for the user (optional).
     * @param username    The new username of the user (optional).
     * @param age         The age of the user (must be greater than 0).
     * @param role        The updated role or permission level of the user.
     * @param icNumber    The updated unique identification number of the user (optional).
     * @param phoneNumber The updated phone number of the user (optional).
     * @return The updated User object, or null if no user is found with the given ID.
     */
    User updateUser(Long userId, String password, String username, int age, UserRole role, String icNumber, String phoneNumber);

    /**
     * Update user performance metrics.
     *
     * @param userId The ID of the user.
     * @param bonus  The additional performance bonus to be added to the user's salary.
     * @return The updated User object, or null if no user is found with the given ID.
     */
    User updatePerformanceBonus(Long userId, double bonus);

    /**
     * Retrieve all users.
     *
     * @return A list of all User objects.
     */
    List<User> getAllUsers();

    /**
     * Retrieve users based on their role or permission level.
     *
     * @param role The role or permission level to filter users.
     * @return A list of User objects with the specified role.
     */
    List<User> getUsersByRole(UserRole role);

    /**
     * Validate user login credentials.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The User object if the credentials are valid, or null if invalid.
     */
    User login(String username, String password);

    /**
     * Search for users by a keyword.
     *
     * @param keyword The keyword to search for in usernames or real names.
     * @return A list of User objects matching the keyword.
     */
    List<User> searchUser(String keyword);
}