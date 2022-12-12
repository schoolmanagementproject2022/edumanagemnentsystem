package com.epam.edumanagementsystem.admin.journal_agenda.rest.service;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Homework;

public interface HomeworkService {

    Homework save(String homework, Long classId, Long courseId);

}
