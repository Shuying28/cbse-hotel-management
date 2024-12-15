package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Config;

import java.util.List;

public interface ConfigService {
    public Config get();
    public boolean update(double managesalary,double  staffsalary, double cleanerssalary, double manage, double staff, double cleaner, double totalmoney, double totalroom);
}
