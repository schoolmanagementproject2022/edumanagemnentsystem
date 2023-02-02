package com.epam.edumanagementsystem.admin.journal.rest.service;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;

import java.util.Optional;

public interface GradesService {

    Grades save(GradesDto gradesDto, String date, Long studentId);

    boolean existByStudentIdAndDate(Long id, String date);

    Optional<Grades> findByStudentId(Long id);

    Grades findByDateAndStudentId(String date, Long studentId);

    void update(GradesDto gradesDto, String date, Long studentId);

}
