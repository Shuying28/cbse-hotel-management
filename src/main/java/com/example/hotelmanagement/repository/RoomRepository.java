package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    List<Course> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM Course c WHERE c.registrationStatus = 'OPEN'")
    List<Course> findAvailableCourses();
}