package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.enums.Enums.UserRole;
import com.example.hotelmanagement.repository.UserRepository;
import com.example.hotelmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User addUser(String username, String password, UserRole role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public User updateUser(Long userId, String password, String username, int age, UserRole role, String icNumber, String phoneNumber) {
        return userRepository.findById(userId).map(user -> {
            if (password != null && !password.isEmpty()) {
                user.setPassword(password);
            }
            if (username != null && !username.isEmpty()) {
                user.setUsername(username);
            }
            if (age > 0) {
                user.setAge(age);
            }
            if (role != null) {
                user.setRole(role);
            }
            if (icNumber != null && !icNumber.isEmpty()) {
                user.setIcNumber(icNumber);
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                user.setPhoneNumber(phoneNumber);
            }
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public User updatePerformanceBonus(Long userId, double bonus) {
        return userRepository.findById(userId).map(user -> {
            user.setSalary(user.getSalary() + bonus);
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    public List<User> searchUser(String keyword) {
        return userRepository.findByUsernameContainingIgnoreCaseOrRealNameContainingIgnoreCase(keyword, keyword);
    }

}
