package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;

public interface AcademicClassService {

    AcademicClassDto save(AcademicClassDto academicClassDto);

    List<AcademicClassDto> findAll();

    AcademicClassDto findById(Long id);

    AcademicClassDto findByClassNumber(String name);

    AcademicClassDto removeByTeacherName(String teacherName);

    AcademicClassDto update(AcademicClassDto academicClassDto);

    Set<AcademicClassDto> findByTeacherId(Long id);

    void checkClassDuplication(AcademicClassDto academicClassDto, BindingResult bindingResult, Model model);

    Set<AcademicClass> findAcademicClassByTeacherId(Long id);

}