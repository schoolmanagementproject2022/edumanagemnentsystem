package com.epam.edumanagementsystem.admin.journal_agenda.rest.service;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Test;

public interface TestService {

    Test save(String testDesc, Long classId, Long courseId);

}
