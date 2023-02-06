package com.epam.edumanagementsystem.admin.journal.rest.repository;

import com.epam.edumanagementsystem.admin.journal.model.entity.Classwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ClassworkRepository extends JpaRepository<Classwork, Long> {

    Classwork findByDateOfClassworkAndAcademicClassIdAndAcademicCourseId(LocalDate date, Long classId, Long courseId);

    Classwork findByClassworkAndAcademicCourseIdAndDateOfClasswork(String classwork, Long courseId, LocalDate date);
}
