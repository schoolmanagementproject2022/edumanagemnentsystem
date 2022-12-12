package com.epam.edumanagementsystem.admin.journal_agenda.rest.service;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Classwork;

public interface ClassworkService {

    Classwork save(String classWork, Long classId, Long courseId);

}
