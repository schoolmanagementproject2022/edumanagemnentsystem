package com.epam.edumanagementsystem.admin.journal.rest.repository;

import com.epam.edumanagementsystem.admin.journal.model.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    Homework findByDateOfHomeworkAndAcademicClassIdAndAcademicCourseId(LocalDate date, Long classId, Long courseId);

    Homework findHomeworkByHomework(String homework);
}
