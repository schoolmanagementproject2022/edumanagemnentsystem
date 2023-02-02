package com.epam.edumanagementsystem.admin.journal.rest.service;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;

import java.util.Optional;

public interface GradesService {

    Grades save(GradesDto gradesDto);

    Optional<Grades> findById(Long id);

    Optional<Grades> findByStudentId(Long id);

}
