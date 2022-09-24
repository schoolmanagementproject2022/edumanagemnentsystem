package com.epam.edumanagementsystem.teacher.impl;

import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void create(TeacherDto teacherDto) {
        teacherRepository.save(TeacherMapper.toTeacher(teacherDto));
    }

    @Override
    public List<TeacherDto> findAll() {
        List<Teacher> allTeachers = teacherRepository.findAll();
        return TeacherMapper.toListOfTeachersDto(allTeachers);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.getTeacherById(id);
    }

    @Override
    public Teacher findByUserId(Long id) {
        return teacherRepository.findByUserId(id);
    }
}