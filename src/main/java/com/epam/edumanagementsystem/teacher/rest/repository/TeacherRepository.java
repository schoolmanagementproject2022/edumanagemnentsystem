package com.epam.edumanagementsystem.teacher.rest.repository;

import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher getTeacherById(Long id);

    @Query(value = "SELECT * FROM teacher WHERE LOWER(email) = LOWER(?1)",nativeQuery = true)
    Optional<Teacher> findByEmail(String email);
}
