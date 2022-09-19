package com.epam.edumanagementsystem.student.rest.repository;

import com.epam.edumanagementsystem.student.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM student WHERE LOWER(email) = LOWER(?1)",nativeQuery = true)
    Optional<Student> findByEmail(String email);
}
