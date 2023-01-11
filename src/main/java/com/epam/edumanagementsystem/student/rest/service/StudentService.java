package com.epam.edumanagementsystem.student.rest.service;

import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    StudentDto create(StudentDto studentDto);

    StudentDto updateFields(StudentDto studentDto);


    List<StudentDto> findAll();

    StudentDto findByUserId(Long id);

    List<Student> findByAcademicClassId(Long id);

    StudentDto findById(Long studentId);

    List<StudentDto> findStudentsWithoutParent();

    List<StudentDto> findStudentsByParentId(Long parentId);

    void addProfilePicture(StudentDto studentDto, MultipartFile multipartFile);

    void deletePic(Long id);

    List<StudentDto> findStudentsWithoutConnectionWithClass();
}
