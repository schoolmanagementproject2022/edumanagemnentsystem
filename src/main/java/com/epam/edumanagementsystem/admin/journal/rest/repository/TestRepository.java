package com.epam.edumanagementsystem.admin.journal.rest.repository;

import com.epam.edumanagementsystem.admin.journal.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Test findByDateOfTestAndAcademicClassIdAndAcademicCourseId(LocalDate date, Long classId, Long courseId);

    Test findByTestAndAcademicCourseIdAndDateOfTest(String test, Long courseId, LocalDate date);
}
