package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;

import java.util.List;
import java.util.Set;

public interface AcademicClassService {

    AcademicClassDto save(AcademicClassDto academicClassDto);

    List<AcademicClassDto> findAll();

    AcademicClass findById(Long id);

    AcademicClass findByClassNumber(String name);

    AcademicClass removeByTeacherName(String teacherName);

    AcademicClassDto update(AcademicClassDto academicClassDto);

    Set<AcademicClassDto> findByTeacherId(Long id);


}