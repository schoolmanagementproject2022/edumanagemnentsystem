package com.epam.edumanagementsystem.teacher.rest.repository;

import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher getTeacherById(Long id);
}
