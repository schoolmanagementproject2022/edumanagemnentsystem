package com.epam.edumanagementsystem.admin.journal.rest.service;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Test;

import java.time.LocalDate;

public interface TestService {

    Test save(SaveAgendaDto saveAgendaDto);

    Test update(SaveAgendaDto saveAgendaDto);

    Test getTestOfCourse(LocalDate date, Long classId, Long courseId);

    Test findByTestDescriptionCourseIdAndDateOdTest(String test, Long courseId, LocalDate date);
}
