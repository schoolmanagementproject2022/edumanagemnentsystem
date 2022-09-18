package com.epam.edumanagementsystem.student.rest.service;

import com.epam.edumanagementsystem.admin.model.entity.Admin;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student create(Student student);

    String delete(StudentDto studentDto);

    StudentDto updateField(StudentDto studentDto);

    StudentDto update(StudentDto studentDto);

    List<StudentDto> findAll();

    StudentDto getById(Long id);

    Optional<Student> findByEmail(String email);
}
