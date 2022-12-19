package com.epam.edumanagementsystem.student.impl;

import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.repository.StudentRepository;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
    @Transactional
    public Student create(StudentDto studentDto) {
        if (studentDto == null) {
            throw new ObjectIsNull();
        }
        return studentRepository.save(
                StudentMapper.toStudent(
                        studentDto,
                        userService.save(
                                new User(studentDto.getEmail(), studentDto.getRole())
                        )
                )
        );
    }

    @Override
    @Transactional
    public StudentDto updateFields(StudentDto studentDto) {
        if (studentDto == null) {
            throw new IllegalArgumentException("@Todo");
        }
        StudentDto updatableStudentDto = findByStudentId(studentDto.getId());
        User userOfStudent = userService.findByEmail(updatableStudentDto.getEmail());
        Student updatableStudent = StudentMapper.toStudent(updatableStudentDto, userOfStudent);
        userOfStudent.setEmail(studentDto.getEmail());

        Student newStudent = StudentMapper.toStudent(studentDto, userOfStudent);
        if (newStudent.getId() != null) {
            updatableStudent.setId(newStudent.getId());
        }
        if (newStudent.getName() != null) {
            updatableStudent.setName(newStudent.getName());
        }
        if (newStudent.getSurname() != null) {
            updatableStudent.setSurname(newStudent.getSurname());
        }
        if (newStudent.getUser() != null) {
            updatableStudent.setUser(newStudent.getUser());
        }
        if (newStudent.getAddress() != null) {
            updatableStudent.setAddress(newStudent.getAddress());
        }
        if (newStudent.getDate() != null) {
            updatableStudent.setDate(newStudent.getDate());
        }
        if (newStudent.getBloodGroup() != null) {
            updatableStudent.setBloodGroup(newStudent.getBloodGroup());
        }
        if (newStudent.getGender() != null) {
            updatableStudent.setGender(newStudent.getGender());
        }
        updatableStudent.setParent(newStudent.getParent());
        updatableStudent.setAcademicClass(newStudent.getAcademicClass());
        Student updatedStudent = studentRepository.save(updatableStudent);
        return StudentMapper.toStudentDto(updatedStudent);
    }

    @Transactional
    @Override
    public Student updateStudentsClass(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("@Todo");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student findByUserId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("@Todo");
        }
        return studentRepository.findByUserId(id);
    }

    @Override
    public List<Student> findByAcademicClassId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("@Todo");
        }
        return studentRepository.findByAcademicClassId(id);
    }

    @Override
    public StudentDto findByStudentId(Long id) {
        return StudentMapper.toStudentDto(studentRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public List<StudentDto> findStudentsByParentId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("@Todo");
        }
        return StudentMapper.toStudentDtoList(studentRepository.findAllByParentId(id));
    }

    @Override
    public List<StudentDto> findStudentsWithoutParent() {
        return findAll().stream().filter(studentDto -> studentDto.getParent() == null).collect(Collectors.toList());
    }

    @Override
    public void addProfilePicture(Student student, MultipartFile multipartFile) {
        if (student == null || multipartFile == null) {
            throw new IllegalArgumentException("@Todo");
        }
        student.setPicUrl(imageService.saveImage(multipartFile));
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deletePic(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("@Todo");
        }
        studentRepository.updateStudentPicUrl(id);
    }

    @Override
    public List<StudentDto> findStudentsWithoutConnectionWithClass() {
        return findAll().stream().filter(studentDto -> studentDto.getAcademicClass() == null).collect(Collectors.toList());
    }
}
