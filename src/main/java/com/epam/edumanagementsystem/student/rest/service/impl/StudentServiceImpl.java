package com.epam.edumanagementsystem.student.rest.service.impl;

import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.dto.StudentEditDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.api.StudentController;
import com.epam.edumanagementsystem.student.rest.repository.StudentRepository;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentServiceImpl(PasswordEncoder passwordEncoder, StudentRepository studentRepository, UserService userService, ImageService imageService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.imageService = imageService;
        this.encoder = passwordEncoder;
    }

    @Override
    public List<StudentDto> findAll() {
        return StudentMapper.toStudentDtoList(studentRepository.findAll());
    }

    @Override
    public StudentDto create(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        student.setUser(userService.save(new User(studentDto.getEmail(), studentDto.getRole())));
        student.setPassword(encoder.encode(studentDto.getPassword()));
        return StudentMapper.toStudentDto(studentRepository.save(student));
    }

    @Transactional
    @Override
    public StudentDto updateFields(StudentEditDto studentDto) {
        studentRepository.findById(studentDto.getId()).orElseThrow(EntityNotFoundException::new);
        StudentDto byName = findById(studentDto.getId());
        User userOfStudent = userService.findByEmail(byName.getEmail());
        Student updatableStudent = StudentMapper.toStudent(byName, userOfStudent);
        userOfStudent.setEmail(studentDto.getEmail());
        Student newStudent = StudentMapper.toStudentForEdit(studentDto, userOfStudent);
        updatableStudent.setId(newStudent.getId());
        updatableStudent.setName(newStudent.getName());
        updatableStudent.setSurname(newStudent.getSurname());
        updatableStudent.setDate(newStudent.getDate());
        updatableStudent.setGender(newStudent.getGender());
        updatableStudent.setParent(newStudent.getParent());
        updatableStudent.setAcademicClass(newStudent.getAcademicClass());
        logger.debug(updatableStudent.getName());
        return StudentMapper.toStudentDto(studentRepository.save(updatableStudent));
    }

    @Override
    public StudentDto updateForPic(StudentDto studentDto) {
        studentRepository.findById(studentDto.getId()).orElseThrow(EntityNotFoundException::new);
        StudentDto byName = findById(studentDto.getId());
        User userOfStudent = userService.findByEmail(byName.getEmail());
        Student updatableStudent = StudentMapper.toStudent(byName, userOfStudent);
        userOfStudent.setEmail(studentDto.getEmail());
        Student newStudent = StudentMapper.toStudent(studentDto, userOfStudent);
        updatableStudent.setId(newStudent.getId());
        updatableStudent.setName(newStudent.getName());
        updatableStudent.setSurname(newStudent.getSurname());
        updatableStudent.setDate(newStudent.getDate());
        updatableStudent.setGender(newStudent.getGender());
        updatableStudent.setParent(newStudent.getParent());
        updatableStudent.setAcademicClass(newStudent.getAcademicClass());
        updatableStudent.setPicUrl(newStudent.getPicUrl());
        logger.debug(updatableStudent.getName());
        return StudentMapper.toStudentDto(studentRepository.save(updatableStudent));
    }

    @Override
    public List<StudentDto> findStudentsByClassName(String name) {
        List<StudentDto> studentsOfConcreteName = new ArrayList<>();
        for (StudentDto studentDto : findAll()) {
            if (studentDto.getAcademicClass() != null && studentDto.getAcademicClass().getClassNumber().equals(name)) {
                studentsOfConcreteName.add(studentDto);
            }
        }
        return studentsOfConcreteName;
    }

    @Override
    public StudentDto findByUserId(Long id) {
        return StudentMapper.toStudentDto(studentRepository.findByUserId(id).get());
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
    public void addProfilePicture(StudentDto studentDto, MultipartFile multipartFile) {
        studentDto.setPicUrl(imageService.saveImage(multipartFile));
        logger.debug(studentDto.getPicUrl());
        updateForPic(studentDto);
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
