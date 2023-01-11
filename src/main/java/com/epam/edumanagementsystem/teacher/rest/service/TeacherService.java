package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherEditDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface TeacherService {

    TeacherDto findById(Long id);

    TeacherDto findByUserId(Long id);

    TeacherEditDto findTeacherEditById(Long id);

    List<TeacherDto> findAll();

    TeacherDto save(TeacherDto teacherDto);

    TeacherDto update(TeacherEditDto teacherDto);

    Set<TeacherDto> findAllTeachersInAllCourses();

    Set<TeacherDto> findAllTeachersInAllClasses();

    Set<TeacherDto> findAllTeachersByClassName(String className);

    Set<TeacherDto> findAllTeachersByCourseName(String courseName);

    void addImage(TeacherDto teacherDto, MultipartFile multipartFile);

    void removeImage(Long id);

}
