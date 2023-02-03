package com.epam.edumanagementsystem.admin.journal.rest.service;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;

import java.util.Optional;

public interface GradesService {

    Grades save(GradesDto gradesDto, String date);

    boolean existByDateStudentIdAndCourseId(String date, Long studentId, Long courseId);

    Optional<Grades> findByStudentId(Long id);

    Grades findByDateStudentIdAndCourseId(String date, Long studentId, Long courseId);

    void update(GradesDto gradesDto, String date);

}
