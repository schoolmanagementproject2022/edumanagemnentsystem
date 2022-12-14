package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal_agenda.model.entity.Classwork;
import com.epam.edumanagementsystem.admin.journal_agenda.rest.repository.ClassworkRepository;
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
class ClassworkServiceImplTest {

    @Mock
    private ClassworkRepository classworkRepository;
    @Mock
    private AcademicClassRepository academicClassRepository;
    @Mock
    private AcademicCourseRepository academicCourseRepository;

    private Classwork classwork;
    private AcademicClass academicClass;
    private AcademicCourse academicCourse;

    @InjectMocks
    private ClassworkServiceImpl classworkService;

    @BeforeEach
    void setUp() {
        academicClass = new AcademicClass();
        academicClass.setId(1L);
        academicClass.setClassNumber("A1");
        academicCourse = new AcademicCourse();
        academicCourse.setId(1L);
        academicCourse.setName("Course 1");
        classwork = new Classwork(1L, "classwork", 5, LocalDate.now(), academicCourse, academicClass);
    }

    @Test
    @DisplayName("Classwork field save")
    void testSaveClasswork() {
        Long classId = academicClass.getId();
        Long courseId = academicCourse.getId();
        when(classworkRepository.save(any(Classwork.class))).thenReturn(classwork);
        when(academicClassRepository.findById(classId)).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(courseId)).thenReturn(Optional.of(academicCourse));
        Classwork actualClasswork = classworkService.save("classwork", classId, courseId);
        Assertions.assertNotNull(actualClasswork);
        assertEquals(classwork.getClasswork(), actualClasswork.getClasswork());
    }

    @Test
    @DisplayName("Classwork field save with null classId and courseId")
    void testSaveByNullId() {
        Long classId = null;
        Long courseId = null;
        when(classworkRepository.save(any(Classwork.class))).thenReturn(classwork);
        when(academicClassRepository.findById(classId)).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(courseId)).thenReturn(Optional.of(academicCourse));
        Classwork actualClasswork = classworkService.save("classwork", classId, courseId);
        assertThat(actualClasswork.getAcademicClass().getId()).isNotNull();
        assertThat(actualClasswork.getAcademicCourse().getId()).isNotNull();
    }
}