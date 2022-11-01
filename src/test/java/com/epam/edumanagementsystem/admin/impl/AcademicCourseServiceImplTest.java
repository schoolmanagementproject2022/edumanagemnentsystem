package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcademicCourseServiceImplTest {


    private AcademicCourse academicCourse;
    private Subject subject;
    private Teacher teacher;
    private AcademicClass academicClass;


    @Mock
    private AcademicCourseRepository academicCourseRepository;

    @InjectMocks
    private AcademicCourseServiceImpl academicCourseService;

    @BeforeEach
    void init() {
        Set<Teacher> teacherSet = new HashSet<>();
        Set<AcademicClass> academicClasses = new HashSet<>();
        academicCourse = new AcademicCourse(1L, "firstAcademicCourse",
                subject, teacherSet, academicClasses);
    }


    @Test
    @DisplayName("should find academic course by given academic name")
    void findAcademicCourseByAcademicCourseName() {
        String name = "firstAcademicCourse";
        when(academicCourseRepository.findAcademicCourseByName(name)).thenReturn(academicCourse);
        AcademicCourse academicCourseByAcademicCourseName =
                academicCourseService.findAcademicCourseByAcademicCourseName(name);
        assertEquals(academicCourse, academicCourseByAcademicCourseName);
    }

    @Test
    void findAllCourse() {
    }

    @Test
    void findByID() {
    }

    @Test
    void create() {
    }

    @Test
    void getById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllTeacher() {
    }

    @Test
    void update() {
    }

    @Test
    void findAllTeachersByAcademicCourseName() {
    }
}