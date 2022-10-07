package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.List;
import java.util.Set;

public interface AcademicClassService {

    void create(AcademicClass academicClass);

    List<AcademicClassDto> findAll();

    AcademicClassDto getById(Long id);

    AcademicClass findByName(String name);

    void update(AcademicClass academicClass);

    Set<AcademicCourse> findAllAcademicCourses(String name);

    Set<Teacher> findAllTeachers(String name);

}


