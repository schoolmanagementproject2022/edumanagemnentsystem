package com.epam.edumanagementsystem.student.impl;

import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final ImageService imageService;

    private final AcademicClassService academicClassService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UserService userService, ImageService imageService, AcademicClassService academicClassService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.imageService = imageService;
        this.academicClassService = academicClassService;
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

        User user = new User();
        user.setEmail(studentDto.getEmail());
        user.setRole(studentDto.getRole());
        User savedUser = userService.save(user);

        return studentRepository.save(StudentMapper.toStudent(studentDto, savedUser));
    }

    @Override
    @Transactional
    public Student create(StudentDto studentDto, UserService userService) {
        if (studentDto == null) {
            throw new ObjectIsNull();
        }
        return studentRepository.save(StudentMapper.toStudent(studentDto, userService));
    }

    @Override
    @Transactional
    public StudentDto updateFields(StudentDto studentDto) {
        if (studentDto == null) {
            throw new ObjectIsNull();
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
            throw new ObjectIsNull();
        }
        return studentRepository.save(student);
    }

    @Override
    public Student findByUserId(Long id) {
        if (id == null) {
            throw new UserNotFoundException();
        }
        return studentRepository.findByUserId(id);
    }

    @Override
    public List<Student> findByAcademicClassId(Long id) {
        if (id == null) {
            throw new UserNotFoundException();
        }
        return studentRepository.findByAcademicClassId(id);
    }

    @Override
    public StudentDto findByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(UserNotFoundException::new);
        return StudentMapper.toStudentDto(student);
    }

    public List<StudentDto> findStudentsByParentId(Long parentId) {
        if (parentId != null) {
            return StudentMapper.toStudentDtoList(studentRepository.findAllByParentId(parentId));
        }
        throw new UserNotFoundException();
    }

    @Override
    public List<Student> findStudentsWithoutParent() {
        return StudentMapper.toStudentList(findAll().stream().filter(student -> student.getParent() == null).collect(Collectors.toList()));
    }

    @Override
    public void addProfilePicture(Student student, MultipartFile multipartFile) {
        student.setPicUrl(imageService.saveImage(multipartFile));
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deletePic(Long id) {
        studentRepository.updateStudentPicUrl(id);
    }

    @Override
    public List<StudentDto> studentsWithoutConnectionWithClass() {
        List<StudentDto> studentsWithoutClass = new ArrayList<>();
        List<StudentDto> allStudents = findAll();
        for (StudentDto student : allStudents) {
            if (null == student.getAcademicClass()) {
                studentsWithoutClass.add(student);
            }
        }
        return studentsWithoutClass;
    }

    @Override
    public List<StudentDto> findStudentsByClassName(String name) {
        List<StudentDto> studentsOfConcreteName = new ArrayList<>();
        for (StudentDto studentDto : findAll()) {
            if (studentDto.getAcademicClass().getClassNumber().equals(name)) {
                studentsOfConcreteName.add(studentDto);
            }
        }
        return studentsOfConcreteName;
    }

}
