package com.epam.edumanagementsystem.admin.journal_agenda.rest.repository;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Test findByDateOfTestAndAcademicClassIdAndAcademicCourseId(LocalDate date, Long classId, Long courseId);

}
