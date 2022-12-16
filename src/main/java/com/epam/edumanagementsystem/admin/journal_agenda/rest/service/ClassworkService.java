package com.epam.edumanagementsystem.admin.journal_agenda.rest.service;

import com.epam.edumanagementsystem.admin.journal_agenda.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Classwork;

import java.time.LocalDate;

public interface ClassworkService {

    Classwork save(SaveAgendaDto saveAgendaDto);

    Classwork update(SaveAgendaDto saveAgendaDto);

    Classwork getClassWorkOfCourse(LocalDate date, Long classId, Long courseId);

}
