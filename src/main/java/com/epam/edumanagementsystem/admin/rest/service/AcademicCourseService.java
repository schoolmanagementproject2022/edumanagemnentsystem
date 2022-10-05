package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.List;
import java.util.Set;

public interface AcademicCourseService {

    void create(AcademicCourse academicCourse);

    AcademicCourseDto getById(Long id);

    List<AcademicCourseDto> findAll();

    AcademicCourse findByName(String name);
    Set<Teacher> findTeacherByAcademicCourseName(String name);

    public void update(AcademicCourse academicCourse);
}