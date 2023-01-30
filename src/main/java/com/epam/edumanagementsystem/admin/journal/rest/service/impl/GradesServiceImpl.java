package com.epam.edumanagementsystem.admin.journal.rest.service.impl;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;
import com.epam.edumanagementsystem.admin.journal.rest.mapper.GradesMapper;
import com.epam.edumanagementsystem.admin.journal.rest.repository.GradesRepository;
import com.epam.edumanagementsystem.admin.journal.rest.service.GradesService;
import org.springframework.stereotype.Service;

@Service
public class GradesServiceImpl implements GradesService {

    private final GradesRepository gradesRepository;

    public GradesServiceImpl(GradesRepository gradesRepository) {
        this.gradesRepository = gradesRepository;
    }

    @Override
    public Grades save(GradesDto gradesDto) {
        return gradesRepository.save(GradesMapper.toGrades(gradesDto));
    }
}
