package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Course;

import java.util.List;

public interface OrderService {
    List<Course> getAllCourses();
    Course saveCourse(Course course);
    Course getCourseById(Long id);
    Course updateCourse(Course course);
    void deleteCourseById(Long id);

    List<Course> getEnrolledCoursesByStudent(Long studentId);

    List<Course> getAvailableCourses();
}