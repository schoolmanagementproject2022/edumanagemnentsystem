package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.SubjectMapper;
import com.epam.edumanagementsystem.admin.model.dto.SubjectDto;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.SubjectRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Mock
    SubjectRepository subjectRepository;
    @InjectMocks
    private SubjectServiceImpl service;
    private Subject subject;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setRole("TEACHER");
        user.setEmail("t@gmail.com");
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherName");
        teacher.setSurname("TeacherSurname");
        teacher.setUser(user);
        teacher.setPassword(user.getEmail());
        teacher.setPassword("password");
        teacher.setAcademicClass(new HashSet<>());
        teacher.setAcademicCourseSet(new HashSet<>());
        Set<Teacher> allTeacher = new HashSet<>();
        allTeacher.add(teacher);
        subject = new Subject(1L, "SubjectName", allTeacher);
    }

    @Test
    void testFindAll() {
        when(subjectRepository.findAll()).thenReturn(List.of(new Subject()));
        List<SubjectDto> subjectDtoList = service.findAll();
        assertNotNull(subjectDtoList);
        assertEquals(1, subjectDtoList.size());
    }

    @Test
    void testSaveSubject() {
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);
        SubjectDto actualSubject = service.save(SubjectMapper.toDto(subject));
        Assertions.assertNotNull(actualSubject);
        assertThat(actualSubject.getId()).isPositive();
    }

    @Test
    void saveSubjectButSubjectNull() {
        Assertions.assertThrows(NullPointerException.class, () -> service.save(null));
    }

    @Test
    void findSubjectBySubjectName() {
        when(subjectRepository.findByName(subject.getName())).thenReturn(Optional.ofNullable(subject));
        SubjectDto actualSubject = service.findByName(subject.getName());
        assertEquals(subject.getName(), actualSubject.getName());
    }

    @Test
    void findByIncorrectSubjectName() {
        when(subjectRepository.findByName(subject.getName())).thenReturn(Optional.ofNullable(subject));
        Subject subject1 = new Subject(1L, "IncorrectName", null);
        SubjectDto actualSubject = service.findByName(subject.getName());
        assertNotEquals(actualSubject.getName(), subject1.getName());
    }

    @Test
    void negativeUpdate() {
        Assertions.assertThrows(NullPointerException.class, () -> service.update(null));
    }

}