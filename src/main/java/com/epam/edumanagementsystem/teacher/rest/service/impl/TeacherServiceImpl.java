package com.epam.edumanagementsystem.teacher.rest.service.impl;


import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherEditDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final PasswordEncoder encoder;

    public TeacherServiceImpl(TeacherRepository teacherRepository, UserService userService,
                              ImageService imageService, PasswordEncoder encoder) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.imageService = imageService;
        this.encoder = encoder;
    }

    @Override
    public TeacherDto findById(Long id) {
        return TeacherMapper.mapToTeacherDto(teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found by id: " + id)));
    }

    @Override
    public TeacherDto findByUserId(Long userId) {
        return TeacherMapper.mapToTeacherDto(teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found by user id: " + userId)));
    }

    @Override
    public TeacherEditDto findTeacherEditById(Long id) {
        return TeacherMapper.mapToTeacherEditDto(teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found by id: " + id)));
    }

    @Override
    public List<TeacherDto> findAll() {
        return TeacherMapper.mapToTeacherDtoList(teacherRepository.findAll());
    }

    @Override
    public TeacherDto save(TeacherDto teacherDto) {
        Teacher teacher = TeacherMapper.mapToTeacher(teacherDto);
        teacher.setUser(userService.save(new User(teacherDto.getEmail(), teacherDto.getRole())));
        teacher.setPassword(encoder.encode(teacherDto.getPassword()));
        return TeacherMapper.mapToTeacherDto(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDto update(TeacherEditDto teacherDto) {
        Teacher teacher = teacherRepository.findById(teacherDto.getId()).get();
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.getUser().setEmail(teacherDto.getEmail());
        return TeacherMapper.mapToTeacherDto(teacherRepository.save(teacher));
    }

    @Override
    public void addImage(TeacherDto teacherDto, MultipartFile multipartFile) {
        teacherDto.setPicUrl(imageService.saveImage(multipartFile));
        teacherRepository.save(TeacherMapper.mapToTeacher(teacherDto));
    }

    @Override
    public void removeImage(Long id) {
        teacherRepository.updateTeacherPicUrl(id);
    }
}