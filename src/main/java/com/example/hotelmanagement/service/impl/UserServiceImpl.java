package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.entity.RoleSalary;
import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.enums.Enums.UserRole;
import com.example.hotelmanagement.repository.RoleSalaryRepository;
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
    @Autowired
    private RoleSalaryRepository roleSalaryRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User addUser(User user) {
        User createdUser = new User();
        createdUser.setUsername(user.getUsername());
        createdUser.setRealName(user.getRealName());
        createdUser.setPassword(user.getPassword());
        createdUser.setRole(user.getRole());
        createdUser.setIcNumber(user.getIcNumber());
        createdUser.setPhoneNumber(user.getPhoneNumber());
        // Retrieve the salary based on the user's role
        RoleSalary roleSalary = roleSalaryRepository.findByRole(user.getRole());
        if (roleSalary != null) {
            createdUser.setSalary(roleSalary.getBaseSalary());
        } else {
            throw new RuntimeException("No salary configuration found for role: " + user.getRole());
        }
        return userRepository.save(createdUser);
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
    public User updateUser(Long userId, User user) {
        return userRepository.findById(userId).map(existingUser -> {
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getRealName() != null && !user.getRealName().isEmpty()) {
                existingUser.setRealName(user.getRealName());
            }
            if (user.getAge() > 0) {
                existingUser.setAge(user.getAge());
            }
            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
                RoleSalary roleSalary = roleSalaryRepository.findByRole(user.getRole());
                if (roleSalary != null) {
                    existingUser.setSalary(roleSalary.getBaseSalary());
                } else {
                    throw new RuntimeException("No salary configuration found for role: " + user.getRole());
                }
            }
            if (user.getIcNumber() != null && !user.getIcNumber().isEmpty()) {
                existingUser.setIcNumber(user.getIcNumber());
            }
            if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
                existingUser.setPhoneNumber(user.getPhoneNumber());
            }
            return userRepository.save(existingUser);
        }).orElse(null);
    }


    @Override
    public User updatePerformanceBonus(Long userId, double bonus) {
        return userRepository.findById(userId).map(user -> {
            user.setTotalPerformance(user.getTotalPerformance() + bonus);
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
