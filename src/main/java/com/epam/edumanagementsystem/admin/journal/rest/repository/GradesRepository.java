package com.epam.edumanagementsystem.admin.journal.rest.repository;

import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GradesRepository extends JpaRepository<Grades, Long> {

    Optional<Grades> findByStudentId(Long id);

    boolean existsGradesByDateAndStudentIdAndAcademicCourseId(LocalDate date, Long studentId, Long courseId);

    Grades findByDateAndStudentIdAndAcademicCourseId(LocalDate date, Long studentId, Long courseId);
}
