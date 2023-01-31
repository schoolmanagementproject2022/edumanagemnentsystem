package com.epam.edumanagementsystem.admin.journal.rest.service;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import org.springframework.ui.Model;

public interface JournalService {

    void openJournal(String date, String startDate, String name, Model model, Long courseId);

    void doNotOpenJournal_timetableIsNotExist(String date, String startDate, String className, Model model);

    void checksUpdateOrSaveObjectsInAgendaAndDoes(SaveAgendaDto agendaDto);
}