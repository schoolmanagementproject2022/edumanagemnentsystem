package com.epam.edumanagementsystem.student.impl;

import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.repository.StudentRepository;
import com.epam.edumanagementsystem.student.rest.service.StudentService;
import com.epam.edumanagementsystem.util.exception.UserNotFoundException;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> findAll() {
        return StudentMapper.toStudentDtoList(studentRepository.findAll());
    }

    @Override
    public Student create(StudentDto studentDto) {
        if (studentDto == null) {
            throw new UserNotFoundException();
        }
        return studentRepository.save(StudentMapper.toStudent(studentDto));
    }

    @Override
    public Student create(StudentDto studentDto, UserService userService) {
        if (studentDto == null) {
            throw new UserNotFoundException();
        }
        return studentRepository.save(StudentMapper.toStudent(studentDto, userService));
    }

    @Override
    public Student update(Student student) {
        if (student == null) {
            throw new UserNotFoundException();
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


}
