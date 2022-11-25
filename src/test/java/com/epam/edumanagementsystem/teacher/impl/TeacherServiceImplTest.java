package com.epam.edumanagementsystem.teacher.impl;

import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import com.epam.edumanagementsystem.util.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private TeacherServiceImpl teacherService;
    private User user;
    private User theUserToBeChanged;
    private Teacher teacher;
    private Teacher theTeacherToBeChanged;

    @BeforeEach
    void setUp() {
        user = new User(null, "gm@gmail.com", "TEACHER");
        teacher = new Teacher(1L, "Text", "Textyan", user, "password", new HashSet(), new HashSet(), new HashSet());
        theUserToBeChanged = new User(1L, "testEmail@example.org", "TEACHER");
        theTeacherToBeChanged = new Teacher(1L, "Name", "Surname", theUserToBeChanged, "password", new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    @Test
    @DisplayName("Check the usage of the findAll() method in the service")
    void canFindAllTeachers() {
        teacherService.findAll();
        verify(teacherRepository).findAll();
    }

    @Test
    @DisplayName("The methode return  the right size of objects")
    void findAll_returnRightObjects() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher, teacher));
        List<TeacherDto> allTeachers = teacherService.findAll();

        assertThat(allTeachers).isNotNull();
        assertThat(allTeachers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Check the usage of the findByUserId() method in the service")
    void findByUserId() {
        teacherService.findByUserId(anyLong());
        verify(teacherRepository).findByUserId(anyLong());
    }

    @Test
    @DisplayName("Create teacher positive case - given all parametrs")
    void canCreateTeacher() {
        when(userService.save(any())).thenReturn(user);
        when(teacherRepository.save(any())).thenReturn(teacher);
        TeacherDto teacherDto = TeacherMapper.toDto(teacher);

        Teacher createdTeacher = teacherService.create(teacherDto);

        assertThat(createdTeacher).isNotNull();
        verify(teacherRepository).save(teacher);
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Negative case for create methode - giving null to user")
    void canNotCreateTeacher() {
        assertThrows(ObjectIsNull.class, () -> teacherService.create(null));
    }

    @Test
    @DisplayName("Update Teacher positive case - given all parameters")
    void testUpdateFields() {
        Optional<Teacher> optionalOfTeacher = Optional.of(teacher);

        when(teacherRepository.save(any())).thenReturn(theTeacherToBeChanged);
        when(teacherRepository.findById(any())).thenReturn(optionalOfTeacher);

        when(userService.findByEmail(any())).thenReturn(user);
        TeacherDto actualUpdateFields = teacherService.updateFields(new TeacherDto());
        assertEquals("testEmail@example.org", actualUpdateFields.getEmail());
        assertEquals("Surname", actualUpdateFields.getSurname());
        assertEquals("Name", actualUpdateFields.getName());
        verify(teacherRepository).findById(any());
    }

    @Test
    @DisplayName("Negative case for update method - given null to user")
    void canNotUpdateTeacher() {
        assertThrows(ObjectIsNull.class, () -> teacherService.updateFields(null));
    }

    @Test
    @DisplayName("Check the usage of the findById() method in the service")
    void findById() {
        Optional<Teacher> optionalOfTeacher = Optional.of(teacher);
        when(teacherRepository.findById(any())).thenReturn(optionalOfTeacher);
        TeacherDto actualFindById = teacherService.findById(1L);
        assertEquals(1L, actualFindById.getId().longValue());
        verify(teacherRepository).findById(any());
    }

    @Test
    @DisplayName("Check the usage of the findById() method in the null case")
    void findByIdNegativeCase() {
        assertThrows(UserNotFoundException.class, () -> teacherService.findById(null));
    }

}