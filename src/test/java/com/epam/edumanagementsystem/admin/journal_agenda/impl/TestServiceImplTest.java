package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Test;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.repository.TestRepository;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private TestRepository testRepository;
    @Mock
    private AcademicClassRepository academicClassRepository;
    @Mock
    private AcademicCourseRepository academicCourseRepository;

    private Test test;
    private AcademicClass academicClass;
    private AcademicCourse academicCourse;

    @InjectMocks
    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        academicClass = new AcademicClass();
        academicClass.setId(1L);
        academicClass.setClassNumber("A1");
        academicCourse = new AcademicCourse();
        academicCourse.setId(1L);
        academicCourse.setName("Course 1");
        test = new Test(1L, "test", 5, LocalDate.now(), academicCourse, academicClass);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test field save")
    void testSaveTest() {
        Long classId = academicClass.getId();
        Long courseId = academicCourse.getId();
        when(testRepository.save(any(Test.class))).thenReturn(test);
        when(academicClassRepository.findById(classId)).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(courseId)).thenReturn(Optional.of(academicCourse));
        Test actualTest = testService.save("test", classId, courseId);
        Assertions.assertNotNull(actualTest);
        assertEquals(test.getTest(), actualTest.getTest());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test field save with null classId and courseId")
    void testSaveByNullId() {
        Long classId = null;
        Long courseId = null;
        when(testRepository.save(any(Test.class))).thenReturn(test);
        when(academicClassRepository.findById(classId)).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(courseId)).thenReturn(Optional.of(academicCourse));
        Test actualTest = testService.save("test", classId, courseId);
        assertThat(actualTest.getAcademicClass().getId()).isNotNull();
        assertThat(actualTest.getCourseOfTest().getId()).isNotNull();
    }
}