package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.util.List;

public interface AcademicCourseService {

    void create(AcademicCourse academicCourse);

    AcademicCourseDto getById(Long id);

    List<AcademicCourseDto> findAll();
}
