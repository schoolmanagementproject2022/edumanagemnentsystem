package com.epam.edumanagementsystem.admin.journal_agenda.rest.service;

import com.epam.edumanagementsystem.admin.journal_agenda.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Test;

import java.time.LocalDate;

public interface TestService {

    Test save(SaveAgendaDto saveAgendaDto);

    Test getTestOfCourse(LocalDate date, Long classId, Long courseId);

}
