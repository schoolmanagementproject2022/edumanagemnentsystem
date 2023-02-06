package com.epam.edumanagementsystem.admin.journal.rest.service;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Homework;

import java.time.LocalDate;

public interface HomeworkService {

    Homework save(SaveAgendaDto saveAgendaDto);

    Homework update(SaveAgendaDto saveAgendaDto);

    Homework getHomeworkOfCourse(LocalDate date, Long classId, Long courseId);

    Homework findByDescriptionAndAcademicCourseIdAndDateOfHomework(String homework, Long courseId, LocalDate date);
}
