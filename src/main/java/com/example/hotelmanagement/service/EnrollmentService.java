package com.example.hotelmanagement.service;

import com.example.hotelmanagement.entity.Course;
import com.example.hotelmanagement.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    void enrollStudentToCourses(Long studentId, List<Long> courseId);
    List<Enrollment> getEnrollmentsByStudent(Long studentId);
    List<Course> getEnrolledCoursesByStudent(Long studentId);

    List<Course> getAvailableCourses(Long studentsId);

}
