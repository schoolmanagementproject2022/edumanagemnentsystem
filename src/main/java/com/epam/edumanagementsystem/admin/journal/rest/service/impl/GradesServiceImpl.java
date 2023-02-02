package com.epam.edumanagementsystem.admin.journal.rest.service.impl;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;
import com.epam.edumanagementsystem.admin.journal.rest.mapper.GradesMapper;
import com.epam.edumanagementsystem.admin.journal.rest.repository.GradesRepository;
import com.epam.edumanagementsystem.admin.journal.rest.service.GradesService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Optional<Grades> findById(Long id) {
        return gradesRepository.findById(id);
    }

    @Override
    public Optional<Grades> findByStudentId(Long id) {
        return gradesRepository.findByStudentId(id);
    }

}
