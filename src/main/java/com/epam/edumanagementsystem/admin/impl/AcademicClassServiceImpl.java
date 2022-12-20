package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AcademicClassServiceImpl implements AcademicClassService {

    private final AcademicClassRepository academicClassRepository;

    @Autowired
    public AcademicClassServiceImpl(AcademicClassRepository academicClassRepository) {
        this.academicClassRepository = academicClassRepository;
    }

    @Override
    public AcademicClass create(AcademicClass academicClass) {
        return academicClassRepository.save(academicClass);
    }

    @Override
    public AcademicClassDto getById(Long id) {
        Optional<AcademicClass> classById = academicClassRepository.findById(id);
        return classById.map(AcademicClassMapper::toDto).orElseGet(AcademicClassDto::new);
    }

    @Override
    public AcademicClass findByName(String name) {
        return academicClassRepository.findByClassNumber(name);
    }

    @Override
    public AcademicClass removeByTeacherName(String teacherName) {
        return academicClassRepository.removeByTeacherName(teacherName);
    }

    @Override
    public AcademicClass update(AcademicClass academicClass) {
        return academicClassRepository.save(academicClass);
    }

    @Override
    public List<AcademicClassDto> findAll() {
        List<AcademicClass> academicClassList = academicClassRepository.findAll();
        return AcademicClassMapper.academicClassDtoList(academicClassList);
    }

    @Override
    public Set<AcademicClass> findByTeacherId(Long id) {
        return academicClassRepository.findAcademicClassByTeacherId(id);
    }

}