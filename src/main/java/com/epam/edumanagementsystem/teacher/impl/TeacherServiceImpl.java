package com.epam.edumanagementsystem.teacher.impl;

import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final UserService userService;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, UserService userService) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Teacher create(TeacherDto teacherDto) {
        if (teacherDto == null) {
            throw new ObjectIsNull("Please, fill the required fields");
        }

        User user = new User();
        user.setEmail(teacherDto.getEmail());
        user.setRole(teacherDto.getRole());
        User save = userService.save(user);

        Teacher teacherEntity = TeacherMapper.toTeacher(teacherDto);
        teacherEntity.setUser(save);
        return teacherRepository.save(teacherEntity);
    }

    @Override
    public List<TeacherDto> findAll() {
        List<Teacher> allTeachers = teacherRepository.findAll();
        return TeacherMapper.toListOfTeachersDto(allTeachers);
    }

    @Override
    public Teacher findByUserId(Long id) {
        return teacherRepository.findByUserId(id);
    }
}