package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Homework;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.repository.HomeworkRepository;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class HomeworkServiceImplTest {

    @Mock
    private HomeworkRepository homeworkRepository;
    @Mock
    private AcademicClassRepository academicClassRepository;
    @Mock
    private AcademicCourseRepository academicCourseRepository;

    private Homework homework;
    private AcademicClass academicClass;
    private AcademicCourse academicCourse;

    @InjectMocks
    private HomeworkServiceImpl homeworkService;

    @BeforeEach
    void setUp() {
        academicClass = new AcademicClass();
        academicClass.setId(1L);
        academicClass.setClassNumber("A1");
        academicCourse = new AcademicCourse();
        academicCourse.setId(1L);
        academicCourse.setName("Course 1");
        homework = new Homework(1L, "homework", 5, LocalDate.now(), academicCourse, academicClass);
    }

    @Test
    @DisplayName("Homework field save")
    void testSaveHomework() {
        Long classId = academicClass.getId();
        Long courseId = academicCourse.getId();
        when(homeworkRepository.save(any(Homework.class))).thenReturn(homework);
        when(academicClassRepository.findById(classId)).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(courseId)).thenReturn(Optional.of(academicCourse));
        Homework actualHomework = homeworkService.save("homework", classId, courseId);
        Assertions.assertNotNull(actualHomework);
        assertEquals(homework.getHomework(), actualHomework.getHomework());
    }

    @Test
    @DisplayName("Homework field save with null classId and courseId")
    void testSaveByNullId() {
        Long classId = null;
        Long courseId = null;
        when(homeworkRepository.save(any(Homework.class))).thenReturn(homework);
        when(academicClassRepository.findById(classId)).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(courseId)).thenReturn(Optional.of(academicCourse));
        Homework actualHomework = homeworkService.save("homework", classId, courseId);
        assertThat(actualHomework.getAcademicClass().getId()).isNotNull();
        assertThat(actualHomework.getCourseOfHomework().getId()).isNotNull();
    }
}