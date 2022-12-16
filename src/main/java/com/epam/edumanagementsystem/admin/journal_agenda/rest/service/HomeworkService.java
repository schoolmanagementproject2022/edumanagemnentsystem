package com.epam.edumanagementsystem.admin.journal_agenda.rest.service;

import com.epam.edumanagementsystem.admin.journal_agenda.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Homework;

import java.time.LocalDate;

public interface HomeworkService {

    Homework save(SaveAgendaDto saveAgendaDto);

    Homework getHomeworkOfCourse(LocalDate date, Long classId, Long courseId);

}
