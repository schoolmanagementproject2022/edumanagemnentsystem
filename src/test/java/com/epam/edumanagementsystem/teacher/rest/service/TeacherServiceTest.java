package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.impl.TeacherServiceImpl;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;
import com.epam.edumanagementsystem.util.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private TeacherServiceImpl teacherService;

    @DisplayName("Check the usage of the findAll() method in the service")
    @Test
    void canFindAllTeachers() {
        // when
        teacherService.findAll();
        // then
        verify(teacherRepository).findAll();
    }
    @DisplayName("Check the usage of the findByUserId() method in the service")
    @Test
    void findByUserId() {
        // when
        teacherService.findByUserId(anyLong());
        // then
        verify(teacherRepository).findByUserId(anyLong());
    }

    @DisplayName("Create teacher positive case - given all parametrs")
    @Test
    void canCreateTeacher() {
        //given
        User user = new User(null, "gm@gmail.com", "TEACHER");
        when(userService.save(any())).thenReturn(user);
        Teacher teacher = new Teacher(1L, "Text", "Textyan", user, "password");
        when(teacherRepository.save(any())).thenReturn(teacher);
        TeacherDto teacherDto = TeacherMapper.toDto(teacher);

        //when
        Teacher createdTeacher = teacherService.create(teacherDto);

        //then
        assertThat(createdTeacher).isNotNull();
        verify(teacherRepository).save(teacher);
        verify(userService).save(user);
    }

    @DisplayName("Negative case for create methode - giving null to user")
    @Test
    void canNotCreateTeacher() {
      //then
        assertThrows(ObjectIsNull.class, () -> teacherService.create(null));
    }
}