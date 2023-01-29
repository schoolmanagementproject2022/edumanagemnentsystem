package com.epam.edumanagementsystem.admin.journal.rest.service;

import com.epam.edumanagementsystem.admin.journal.model.entity.Grade;
import org.springframework.stereotype.Service;

public interface GradeService {
    Grade saveGrade(Grade grade);
}
