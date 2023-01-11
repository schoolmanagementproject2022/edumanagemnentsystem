package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;

public interface AcademicCourseService {

    AcademicCourseDto findByName(String name);

    AcademicCourseDto findById(Long id);

    AcademicCourseDto save(AcademicCourseDto academicCourse);

    List<AcademicCourseDto> findAll();

    List<AcademicCourseDto> findAllAcademicCoursesInClassByName(String name);

    AcademicCourseDto update(AcademicCourseDto academicCourse);

    Set<AcademicCourseDto> findAllByTeacherId(Long id);

    void checkCourseDuplication(AcademicCourseDto academicCourseDto, BindingResult bindingResult, Model model);
}