package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    Optional<Teacher> findById(Long id);

    Teacher create(TeacherDto teacherDto);

    List<TeacherDto> findAll();

    Teacher findByUserId(Long id);
}
