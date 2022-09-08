package com.epam.edumanagementsystem.student.impl;

import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.repository.StudentRepository;
import com.epam.edumanagementsystem.student.rest.service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentServices {

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
    public StudentDto getById(Long id) {
        return StudentMapper.toStudentDto(studentRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public String delete(StudentDto studentDto) {
        studentRepository.deleteById(studentDto.getId());
        return studentDto.getNameAndSurname() + " is Deleted";
    }

    @Override
    @Transactional
    public StudentDto updateField(StudentDto studentDto) {
        Student student = studentRepository.findById(studentDto.getId()).orElseThrow(RuntimeException::new);
        if (studentDto.getName() != null) {
            student.setName(studentDto.getName());
        }
        if (studentDto.getSurname() != null) {
            student.setSurname(studentDto.getSurname());
        }
        if (studentDto.getName() != null) {
            student.setEmail(studentDto.getEmail());
        }
        if (studentDto.getGeneratePassword() != null) {
            student.setGeneratePassword(studentDto.getGeneratePassword());
        }
        if (studentDto.getAddress() != null) {
            student.setAddress(studentDto.getAddress());
        }
        if (studentDto.getDate() != null) {
            student.setDate(studentDto.getDate());
        }
        if (studentDto.getBloodGroup() != null) {
            student.setBloodGroup(studentDto.getBloodGroup());
        }
        if (studentDto.getParent() != null) {
            student.setParent(studentDto.getParent());
        }
        if (studentDto.getGender() != null) {
            student.setGender(studentDto.getGender());
        }
        return StudentMapper.toStudentDto(studentRepository.save(student));
    }

    @Override
    @Transactional
    public StudentDto update(StudentDto studentDto) {
        Student student = studentRepository.findById(studentDto.getId()).orElseThrow(RuntimeException::new);
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setEmail(studentDto.getEmail());
        student.setGeneratePassword(studentDto.getGeneratePassword());
        student.setAddress(studentDto.getAddress());
        student.setDate(studentDto.getDate());
        student.setBloodGroup(studentDto.getBloodGroup());
        student.setParent(studentDto.getParent());
        student.setGender(studentDto.getGender());
        return StudentMapper.toStudentDto(studentRepository.save(student));
    }

}
