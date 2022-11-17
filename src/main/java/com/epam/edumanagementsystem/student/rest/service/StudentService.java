package com.epam.edumanagementsystem.student.rest.service;

import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.util.service.UserService;

import java.util.List;

public interface StudentService {

    Student create(StudentDto studentDto);

    Student create(StudentDto studentDto, UserService userService);

    StudentDto updateFields(StudentDto studentDto);

    Student updateStudentsClass(Student student);

    List<StudentDto> findAll();

    Student findByUserId(Long id);

    List<Student> findByAcademicClassId(Long id);

    StudentDto findByStudentId(Long studentId);
}
