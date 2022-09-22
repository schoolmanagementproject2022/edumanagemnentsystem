package com.epam.edumanagementsystem.student.rest.service;

import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;

import java.util.List;

public interface StudentService {

    Student create(StudentDto studentDto);

    String delete(StudentDto studentDto);

    StudentDto updateField(StudentDto studentDto);

    StudentDto update(StudentDto studentDto);

    List<StudentDto> findAll();

    StudentDto getById(Long id);
}
