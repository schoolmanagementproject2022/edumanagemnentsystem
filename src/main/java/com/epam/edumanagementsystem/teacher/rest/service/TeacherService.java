package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.List;

public interface TeacherService {
    void create(Teacher teacher);

    List<TeacherDto> findAll();

    TeacherDto getTeacherById(Long id);
}
