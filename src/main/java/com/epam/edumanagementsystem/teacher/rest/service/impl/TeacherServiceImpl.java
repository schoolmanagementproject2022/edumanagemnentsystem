package com.epam.edumanagementsystem.teacher.rest.service.impl;

import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.admin.rest.service.AcademicClassService;
import com.epam.edumanagementsystem.admin.rest.service.AcademicCourseService;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherEditDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.teacher.rest.service.TeacherService;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final TeacherRepository teacherRepository;
    private final AcademicCourseRepository academicCourseRepository;
    private final AcademicClassRepository academicClassRepository;
    private final AcademicCourseService academicCourseService;
    private final AcademicClassService academicClassService;
    private final UserService userService;
    private final ImageService imageService;
    private final PasswordEncoder encoder;

    public TeacherServiceImpl(TeacherRepository teacherRepository, AcademicCourseRepository academicCourseRepository, AcademicClassRepository academicClassRepository, AcademicCourseService academicCourseService, AcademicClassService academicClassService, UserService userService,
                              ImageService imageService, PasswordEncoder encoder) {
        this.teacherRepository = teacherRepository;
        this.academicCourseRepository = academicCourseRepository;
        this.academicClassRepository = academicClassRepository;
        this.academicCourseService = academicCourseService;
        this.academicClassService = academicClassService;
        this.userService = userService;
        this.imageService = imageService;
        this.encoder = encoder;
    }

    @Override
    public TeacherDto findById(Long id) {
        LOGGER.info("findById method entered: {}", id);
        return TeacherMapper.mapToTeacherDto(teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found by id: " + id)));
    }

    @Override
    public TeacherDto findByUserId(Long userId) {
        LOGGER.info("findByUserId method entered: {}", userId);
        return TeacherMapper.mapToTeacherDto(teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found by user id: " + userId)));
    }

    @Override
    public TeacherEditDto findTeacherEditById(Long id) {
        LOGGER.info("findTeacherEditById method entered: {}", id);
        return TeacherMapper.mapToTeacherEditDto(teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found by id: " + id)));
    }

    @Override
    public List<TeacherDto> findAll() {
        LOGGER.info("findAll method entered:");
        return TeacherMapper.mapToTeacherDtoList(teacherRepository.findAll());
    }

    @Override
    public Set<TeacherDto> findAllTeachersInAllCourses() {
        return TeacherMapper.mapToTeacherDtoSet(academicCourseRepository.findAll()
                .stream()
                .flatMap(academicCourse -> academicCourse.getTeachers().stream())
                .collect(Collectors.toSet()));
    }

    @Override
    public Set<TeacherDto> findAllTeachersInAllClasses() {
        return TeacherMapper.mapToTeacherDtoSet(academicClassRepository.findAll()
                .stream()
                .flatMap(academicClass -> academicClass.getTeachers().stream())
                .collect(Collectors.toSet()));
    }

    @Override
    public Set<TeacherDto> findAllTeachersByClassName(String name) {
        return TeacherMapper.mapToTeacherDtoSet(academicClassService.findByClassNumber(name).getTeachers());
    }

    @Override
    public Set<TeacherDto> findAllTeachersByCourseName(String name) {
        return TeacherMapper.mapToTeacherDtoSet(academicCourseService.findByName(name).getTeachers());
    }

    @Override
    public TeacherDto save(TeacherDto teacherDto) {
        LOGGER.info("save method entered: {}", teacherDto);
        Teacher teacher = TeacherMapper.mapToTeacher(teacherDto);
        teacher.setUser(userService.save(new User(teacherDto.getEmail(), teacherDto.getRole())));
        teacher.setPassword(encoder.encode(teacherDto.getPassword()));
        return TeacherMapper.mapToTeacherDto(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDto update(TeacherEditDto teacherDto) {
        LOGGER.info("update method entered: {}", teacherDto);
        Teacher teacher = teacherRepository.findById(teacherDto.getId()).get();
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.getUser().setEmail(teacherDto.getEmail());
        return TeacherMapper.mapToTeacherDto(teacherRepository.save(teacher));
    }

    @Override
    public void addImage(TeacherDto teacherDto, MultipartFile multipartFile) {
        LOGGER.info("addImage method entered: {}", teacherDto);
        Teacher teacher = teacherRepository.findById(teacherDto.getId()).get();
        teacher.setPicUrl(imageService.saveImage(multipartFile));
        teacherRepository.save(teacher);
    }

    @Override
    public void removeImage(Long id) {
        LOGGER.info("removeImage method entered: {}", id);
        teacherRepository.updateTeacherPicUrl(id);
    }

}