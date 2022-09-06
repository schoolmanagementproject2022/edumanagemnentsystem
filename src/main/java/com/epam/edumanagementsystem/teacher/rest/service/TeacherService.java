package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;

import java.util.List;


public interface TeacherService {

    void create(TeacherDto teacherDto);

    List<TeacherDto> findAll();

}
