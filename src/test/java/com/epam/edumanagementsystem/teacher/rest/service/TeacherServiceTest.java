package com.epam.edumanagementsystem.teacher.rest.service;

import com.epam.edumanagementsystem.teacher.impl.TeacherServiceImpl;
import com.epam.edumanagementsystem.teacher.mapper.TeacherMapper;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.teacher.rest.repository.TeacherRepository;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
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
    void canCreateTeacher() {

        User user = new User(1L, "gm@gmail.com", "TEACHER");

        Teacher teacher = new Teacher(1L,"Text","Textyan",user,"password");

        Teacher createdTeacher = teacherService.create(TeacherMapper.toDto(teacher));

        verify(teacherRepository).save(createdTeacher);

//        //given
////        TeacherDto teacher = new TeacherDto(1L, "Julia", "Gevorgyan",
////                "julgev@gmail.com", "TEACHER", "1%k8H$5+9");
//        User user = mock(User.class);
//        Teacher teacher = mock(Teacher.class);
//        teacher.setUser(user);
//
////                new User(1L, "julgev@gmail.com","TEACHER");
////        Teacher teacher = new Teacher(1L, "Julia", "Gevorgyan", user, "1%k8H$5+9");
////        TeacherDto teacherDto = TeacherMapper.toDto(teacher);
//        //when
//        teacherService.create(TeacherMapper.toDto(teacher));
//        //then
//        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
//        verify(teacherRepository).save(teacherArgumentCaptor.capture());
//
//        Teacher capturedTeacher = teacherArgumentCaptor.getValue();
//
//        assertThat(capturedTeacher).isEqualTo(teacher);


    }

    @Test
    void findByUserId() {
    }
}