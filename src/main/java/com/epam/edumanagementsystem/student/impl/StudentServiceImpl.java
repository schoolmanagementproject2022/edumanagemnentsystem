package com.epam.edumanagementsystem.student.impl;

import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.repository.StudentRepository;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UserService userService, ImageService imageService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public List<StudentDto> findAll() {
        return StudentMapper.toStudentDtoList(studentRepository.findAll());
    }

    @Override
    public Student create(StudentDto studentDto) {
        return studentRepository.save(StudentMapper.toStudent(studentDto, userService.save(
                                new User(studentDto.getEmail(), studentDto.getRole())
                        )
                )
        );
    }

    @Override
    public StudentDto updateFields(StudentDto studentDto) {
        studentRepository.updateField(studentDto.getName(), studentDto.getSurname(),
                userService.findByEmail(studentDto.getEmail()), studentDto.getAddress(), studentDto.getDate(),
                studentDto.getGender(), studentDto.getPassword(), studentDto.getBloodGroup(),
                studentDto.getParent(), studentDto.getAcademicClass(), studentDto.getId());
        return findById(studentDto.getId());
    }

    @Override
    public Student updateStudentsClass(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findByUserId(Long id) {
        return studentRepository.findByUserId(id);
    }

    @Override
    public List<Student> findByAcademicClassId(Long id) {
        return studentRepository.findByAcademicClassId(id);
    }

    @Override
    public StudentDto findById(Long id) {
        return StudentMapper.toStudentDto(studentRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public List<StudentDto> findStudentsByParentId(Long id) {
        return StudentMapper.toStudentDtoList(studentRepository.findAllByParentId(id));
    }

    @Override
    public List<StudentDto> findStudentsWithoutParent() {
        return StudentMapper.toStudentDtoList(studentRepository.findAllByParentIsNull());
    }

    @Override
    public void addProfilePicture(Student student, MultipartFile multipartFile) {
        student.setPicUrl(imageService.saveImage(multipartFile));
        studentRepository.save(student);
    }

    @Override
    public void deletePic(Long id) {
        studentRepository.updateStudentPicUrl(id);
    }

    @Override
    public List<StudentDto> findStudentsWithoutConnectionWithClass() {
        return StudentMapper.toStudentDtoList(studentRepository.findAllByAcademicClassIsNull());
    }
}
