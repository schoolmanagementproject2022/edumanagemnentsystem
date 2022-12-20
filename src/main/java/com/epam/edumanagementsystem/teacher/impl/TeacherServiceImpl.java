package com.epam.edumanagementsystem.teacher.impl;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final UserService userService;
    private final AcademicClassService academicClassService;
    private final AcademicCourseRepository academicCourseRepository;
    private final AcademicCourseService academicCourseService;
    private final ImageService imageService;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, UserService userService, AcademicClassService academicClassService, AcademicCourseRepository academicCourseRepository, AcademicCourseService academicCourseService, ImageService imageService) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.academicClassService = academicClassService;
        this.academicCourseRepository = academicCourseRepository;
        this.academicCourseService = academicCourseService;
        this.imageService = imageService;
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
        User savedUser = userService.save(user);

        Teacher teacherEntity = TeacherMapper.toTeacher(teacherDto, savedUser);
        return teacherRepository.save(teacherEntity);
    }

    @Override
    @Transactional
    public TeacherDto updateFields(TeacherDto teacherDto) {
        if (teacherDto == null) {
            throw new ObjectIsNull();
        }
        TeacherDto updatableTeacherDto = findById(teacherDto.getId());
        User userOfTeacher = userService.findByEmail(updatableTeacherDto.getEmail());
        Teacher updatableTeacher = TeacherMapper.toTeacher(updatableTeacherDto, userOfTeacher);
        userOfTeacher.setEmail(teacherDto.getEmail());

        Teacher newTeacher = TeacherMapper.toTeacher(teacherDto, userOfTeacher);

        if (newTeacher.getId() != null) {
            updatableTeacher.setId(newTeacher.getId());
        }
        if (newTeacher.getName() != null) {
            updatableTeacher.setName(newTeacher.getName());
        }
        if (newTeacher.getSurname() != null) {
            updatableTeacher.setSurname(newTeacher.getSurname());
        }
        if (newTeacher.getUser() != null) {
            updatableTeacher.setUser(newTeacher.getUser());
        }
        Teacher updatedTeacher = teacherRepository.save(updatableTeacher);
        return TeacherMapper.toDto(updatedTeacher);
    }

    @Override
    public TeacherDto findById(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return TeacherMapper.toDto(teacher);
    }

    @Override
    public List<TeacherDto> findAll() {
        return TeacherMapper.toListOfTeachersDto(teacherRepository.findAll());
    }

    @Override
    public Teacher findByUserId(Long id) {
        if (id == null) {
            throw new UserNotFoundException("The Id was null");
        }
        return teacherRepository.findByUserId(id);
    }

    @Override
    public Set<Teacher> findAllTeachersInClass(String name) {
        return academicClassService.findByName(name).getTeacher();
    }

    @Override
    public Set<Teacher> findAllTeachersByCourseName(String name) {
        return academicCourseService.findByName(name).getTeacher();
    }

    @Override
    public Set<Teacher> findAllTeachersInAllClasses() {
        Set<Teacher> teachersByAcademicClass = new HashSet<>();
        List<AcademicClassDto> academicClasses = academicClassService.findAll();
        for (AcademicClassDto academicClass : academicClasses) {
            Set<Teacher> result = academicClass.getTeacherSet();
            teachersByAcademicClass.addAll(result);
        }
        return teachersByAcademicClass;
    }

    @Override
    public Set<Teacher> findAllTeachersInAllCourses() {
        Set<Teacher> teachersByAcademicCourse = new HashSet<>();
        for (AcademicCourse academicCourse : academicCourseRepository.findAll()) {
            teachersByAcademicCourse.addAll(academicCourse.getTeacher());
        }
        return teachersByAcademicCourse;
    }

    @Override
    public void addProfilePicture(Teacher teacher, MultipartFile multipartFile) {
        teacher.setPicUrl(imageService.saveImage(multipartFile));
        teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public void deletePic(Long id) {
        teacherRepository.updateTeacherPicUrl(id);
    }
}