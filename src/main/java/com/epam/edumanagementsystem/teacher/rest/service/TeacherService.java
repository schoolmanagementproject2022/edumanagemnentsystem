package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher create(TeacherDto teacherDto);
    TeacherDto updateFields(TeacherDto teacherDto);
    TeacherDto findById(Long id);
    List<TeacherDto> findAll();
    Teacher findByUserId(Long id);
}
