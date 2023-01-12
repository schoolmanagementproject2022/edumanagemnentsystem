package com.epam.edumanagementsystem.teacher.rest.service.impl;

import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherEditDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private User user;
    private Teacher teacher;
    private Teacher updatedTeacher;

    @BeforeEach
    void setUp() {
        user = new User("teacher@mail.com", "TEACHER");

        teacher = new Teacher(1L, "Teacher", "Teacheryan", user,
                "password", new HashSet<>(), new HashSet<>(), new HashSet<>());

        updatedTeacher = new Teacher(1L, "Teacher", "Teacheryan", user,
                "password");
    }

    @Test
    void findByIdReturnsRightEntity() {
        Long id = 1L;
        Teacher expectedTeacher = teacher;
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));

        TeacherDto actualTeacher = teacherService.findById(id);

        assertThat(actualTeacher).isNotNull();
        assertEquals(TeacherMapper.mapToTeacherDto(expectedTeacher), actualTeacher);
        assertThat(expectedTeacher.getUser().getEmail().equalsIgnoreCase(actualTeacher.getEmail()) &&
                expectedTeacher.getUser().getRole().equalsIgnoreCase(actualTeacher.getRole())).isTrue();
    }

    @Test
    void findByIdThrowsEx() {
        assertThrows(EntityNotFoundException.class, () -> teacherService.findById(2L));
    }

    @Test
    void findByUserIdPositiveCase() {
        Teacher expectedTeacher = teacher;
        when(teacherRepository.findByUserId(1L)).thenReturn(Optional.ofNullable(teacher));

        TeacherDto actualTeacher = teacherService.findByUserId(1L);

        Assertions.assertNotNull(actualTeacher);
        Assertions.assertEquals(TeacherMapper.mapToTeacherDto(expectedTeacher), actualTeacher);
    }

    @Test
    void findByUserIdThrowsEx() {
        assertThrows(EntityNotFoundException.class, () -> teacherService.findByUserId(2L));
    }

    @Test
    void findTeacherEditByIdPositiveCase() {
        Teacher expectedTeacher = teacher;
        when(teacherRepository.findById(1L)).thenReturn(Optional.ofNullable(teacher));

        TeacherEditDto actualTeacher = teacherService.findTeacherEditById(1L);

        Assertions.assertNotNull(actualTeacher);
        Assertions.assertEquals(TeacherMapper.mapToTeacherEditDto(expectedTeacher), actualTeacher);
    }

    @Test
    void findTeacherEditByIdThrowsEx() {
        assertThrows(EntityNotFoundException.class, () -> teacherService.findTeacherEditById(2L));
    }

    @Test
    void saveReturnsRightTeacher() {
        String expectedEmail = "teacher@mail.com";
        when(userService.save(any())).thenReturn(user);
        when(teacherRepository.save(any())).thenReturn(teacher);

        TeacherDto savedTeacher = teacherService.save(TeacherMapper.mapToTeacherDto(teacher));

        Assertions.assertNotNull(savedTeacher);
        assertEquals(expectedEmail, savedTeacher.getEmail());
    }

    @Test
    void findAllNotNullAndSizePositiveCase() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        List<TeacherDto> all = teacherService.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void findAllEmptyCase() {
        List<TeacherDto> all = teacherService.findAll();
        Assertions.assertEquals(0, all.size());
    }

    @Test
    void updateTeacherWithNewName() {
        Long id = 1L;
        String name = "testName";
        updatedTeacher.setName(name);
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any())).thenReturn(updatedTeacher);

        Teacher teacher = teacherRepository.findById(id).get();
        teacher.setName(name);
        TeacherDto updatedTeacherDto = teacherService.update(TeacherMapper.mapToTeacherEditDto(teacher));

        assertThat(updatedTeacherDto.getName()).isEqualTo(name);
    }

    @Test
    void updateTeacherWithNewSurname() {
        Long id = 1L;
        String surname = "testSurname";
        updatedTeacher.setSurname(surname);
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any())).thenReturn(updatedTeacher);

        Teacher teacher = teacherRepository.findById(id).get();
        teacher.setSurname(surname);
        TeacherDto updatedTeacherDto = teacherService.update(TeacherMapper.mapToTeacherEditDto(teacher));

        assertThat(updatedTeacherDto.getSurname()).isEqualTo(surname);
    }

    @Test
    void updateTeacherWithNewEmail() {
        Long id = 1L;
        String email = "test-email@gmail.com";
        updatedTeacher.getUser().setEmail(email);
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any())).thenReturn(updatedTeacher);

        Teacher teacher = teacherRepository.findById(id).get();
        teacher.getUser().setEmail(email);
        TeacherDto updatedTeacherDto = teacherService.update(TeacherMapper.mapToTeacherEditDto(teacher));

        assertThat(updatedTeacherDto.getEmail()).isEqualTo(email);
    }
}