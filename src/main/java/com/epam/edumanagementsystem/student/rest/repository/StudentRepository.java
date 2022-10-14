package com.epam.edumanagementsystem.student.rest.repository;

import com.epam.edumanagementsystem.student.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(Long id);

    List<Student> findByAcademicClassId(Long id);
}
