package com.epam.edumanagementsystem.student.mapper;

import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.dto.StudentEditDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentMapper {
    private StudentMapper() {
    }

    public static Student toStudent(StudentDto studentDto, User user) {
        if (studentDto == null) {
            throw new ObjectIsNull();
        }
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setDate(studentDto.getDate());
        student.setAddress(studentDto.getAddress());
        student.setBloodGroup(studentDto.getBloodGroup());
        student.setGender(studentDto.getGender());
        student.setPassword(studentDto.getPassword());
        student.setParent(studentDto.getParent());
        student.setAcademicClass(studentDto.getAcademicClass());
        student.setUser(user);
        student.setPicUrl(studentDto.getPicUrl());
        return student;
    }

    public static Student toStudentForEdit(StudentEditDto studentDto, User user) {
        if (studentDto == null) {
            throw new ObjectIsNull();
        }
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setDate(studentDto.getDate());
        student.setAddress(studentDto.getAddress());
        student.setBloodGroup(studentDto.getBloodGroup());
        student.setGender(studentDto.getGender());
        student.setParent(studentDto.getParent());
        student.setAcademicClass(studentDto.getAcademicClass());
        student.setUser(user);
        student.setPicUrl(studentDto.getPicUrl());
        return student;
    }

    public static StudentDto toStudentDto(Student student) {
        if (student == null) {
            throw new ObjectIsNull();
        }
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setSurname(student.getSurname());
        studentDto.setEmail(student.getUser().getEmail());
        studentDto.setRole(student.getUser().getRole());
        studentDto.setDate(student.getDate());
        studentDto.setAddress(student.getAddress());
        studentDto.setBloodGroup(student.getBloodGroup());
        studentDto.setGender(student.getGender());
        studentDto.setPassword(student.getPassword());
        studentDto.setParent(student.getParent());
        studentDto.setAcademicClass(student.getAcademicClass());
        studentDto.setPicUrl(student.getPicUrl());
        return studentDto;
    }
    public static StudentEditDto toStudentEditDto(Student student) {
        if (student == null) {
            throw new ObjectIsNull();
        }
        StudentEditDto studentDto = new StudentEditDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setSurname(student.getSurname());
        studentDto.setEmail(student.getUser().getEmail());
        studentDto.setRole(student.getUser().getRole());
        studentDto.setDate(student.getDate());
        studentDto.setAddress(student.getAddress());
        studentDto.setBloodGroup(student.getBloodGroup());
        studentDto.setGender(student.getGender());
        studentDto.setParent(student.getParent());
        studentDto.setAcademicClass(student.getAcademicClass());
        studentDto.setPicUrl(student.getPicUrl());
        return studentDto;
    }

    public static List<StudentDto> toStudentDtoList(List<Student> studentEntities) {
        return studentEntities
                .stream()
                .map(StudentMapper::toStudentDto)
                .collect(Collectors
                        .toList());
    }

    public static Student mapToStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setDate(studentDto.getDate());
        student.setAddress(studentDto.getAddress());
        student.setBloodGroup(studentDto.getBloodGroup());
        student.setGender(studentDto.getGender());
        student.setPassword(studentDto.getPassword());
        student.setParent(studentDto.getParent());
        student.setAcademicClass(studentDto.getAcademicClass());
        student.setPicUrl(studentDto.getPicUrl());
        return student;
    }

    public static Set<StudentDto> toStudentDtoSet(Set<Student> studentEntities) {
        return studentEntities
                .stream()
                .map(StudentMapper::toStudentDto)
                .collect(Collectors
                        .toSet());
    }
}