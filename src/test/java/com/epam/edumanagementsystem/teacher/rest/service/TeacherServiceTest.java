package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.impl.TeacherServiceImpl;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherServiceImpl(teacherRepository);
    }

    @Test
    void canFindAllTeachers() {
        // when
        teacherService.findAll();
        // then
        verify(teacherRepository).findAll();
    }

    @Test
    void create() {
        //given
        //when
        //then
    }

    @Test
    void findByUserId() {
    }
}