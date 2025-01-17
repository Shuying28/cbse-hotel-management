package com.example.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotelmanagement.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
