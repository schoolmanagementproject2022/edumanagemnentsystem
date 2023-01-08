package com.epam.edumanagementsystem.admin.journal_agenda.rest.service;

import org.springframework.ui.Model;

public interface JournalService {

    void openJournal(String date, String startDate, String name, Model model);

    void doNotOpenJournal_timetableIsNotExist(String date, String startDate, String className, Model model);


}
