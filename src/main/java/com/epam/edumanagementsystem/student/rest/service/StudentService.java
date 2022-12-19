package com.epam.edumanagementsystem.student.rest.service;

import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    Student create(StudentDto studentDto);

    StudentDto updateFields(StudentDto studentDto);

    Student updateStudentsClass(Student student);

    List<StudentDto> findAll();

    Student findByUserId(Long id);

    List<Student> findByAcademicClassId(Long id);

    StudentDto findByStudentId(Long studentId);

    List<StudentDto> findStudentsWithoutParent();

    List<StudentDto> findStudentsByParentId(Long parentId);

    void addProfilePicture(Student student, MultipartFile multipartFile);

    void deletePic(Long id);

    List<StudentDto> findStudentsWithoutConnectionWithClass();
}
