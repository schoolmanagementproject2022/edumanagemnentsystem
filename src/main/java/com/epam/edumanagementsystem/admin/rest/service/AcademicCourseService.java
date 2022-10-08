package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.List;
import java.util.Set;

public interface AcademicCourseService {

    AcademicCourse findAcademicCourseByAcademicCourseName(String name);

    List<AcademicCourse> findAllCourse();
    AcademicCourse findByID(Long id);

    void create(AcademicCourse academicCourse);

    AcademicCourseDto getById(Long id);

    List<AcademicCourseDto> findAll();

    void update(AcademicCourse academicCourse);

    Set<Teacher> findAllTeacher();

    Set<Teacher> findAllTeachersByAcademicCourseName(String name);
}