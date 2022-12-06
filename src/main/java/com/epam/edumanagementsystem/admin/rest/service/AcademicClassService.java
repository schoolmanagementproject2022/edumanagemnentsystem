package com.epam.edumanagementsystem.admin.rest.service;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AcademicClassService {

    AcademicClass create(AcademicClass academicClass);

    List<AcademicClassDto> findAll();

    AcademicClassDto getById(Long id);

    AcademicClass findByName(String name);

    AcademicClass update(AcademicClass academicClass);

    List<AcademicCourse> findAllAcademicCourses(String name);

    Set<Teacher> findAllTeachers(String name);

    Set<Teacher> findAllTeacher();

    Set<AcademicClass> findAcademicClassByTeacherId(Long id);

    LocalDate recurs(LocalDate localDate);

}