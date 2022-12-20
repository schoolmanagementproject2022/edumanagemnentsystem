package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface TeacherService {

    Teacher create(TeacherDto teacherDto);

    TeacherDto updateFields(TeacherDto teacherDto);

    TeacherDto findById(Long id);

    List<TeacherDto> findAll();

    Teacher findByUserId(Long id);

    void addProfilePicture(Teacher teacher, MultipartFile multipartFile);

    void deletePic(Long id);

    Set<Teacher> findAllTeachers(String name);

    Set<Teacher> findAllTeachersInClasses();

}
