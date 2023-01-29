package com.epam.edumanagementsystem.admin.journal.rest.service.impl;

import com.epam.edumanagementsystem.admin.journal.model.entity.Grade;
import com.epam.edumanagementsystem.admin.journal.rest.repository.GradeRepository;
import com.epam.edumanagementsystem.admin.journal.rest.service.GradeService;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }


    @Override
    public Grade saveGrade(Grade grade) {
       return gradeRepository.save(grade);
    }
}
