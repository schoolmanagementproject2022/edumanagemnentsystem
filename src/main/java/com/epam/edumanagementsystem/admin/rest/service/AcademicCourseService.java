package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.util.List;
import java.util.Set;

public interface AcademicCourseService {

    AcademicCourse findByName(String name);

    AcademicCourse findByID(Long id);

    AcademicCourseDto save(AcademicCourseDto academicCourse);

    List<AcademicCourseDto> findAll();

    List<AcademicCourse> findAllAcademicCoursesInClassByName(String name);

    AcademicCourseDto update(AcademicCourseDto academicCourse);

    Set<AcademicCourseDto> findAllByTeachersId(Long id);

}