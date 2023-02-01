package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Test;
import com.epam.edumanagementsystem.admin.journal.rest.repository.TestRepository;
import com.epam.edumanagementsystem.admin.journal.rest.service.impl.TestServiceImpl;
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
import java.util.List;
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
    private SaveAgendaDto saveAgendaDto;

    @InjectMocks
    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        saveAgendaDto = new SaveAgendaDto("classwork", "homework", "test");
        academicClass = new AcademicClass();
        academicClass.setId(1L);
        academicClass.setClassNumber("A1");
        academicCourse = new AcademicCourse();
        academicCourse.setId(1L);
        academicCourse.setName("Course 1");
        test = new Test(1L, "test", List.of(), LocalDate.now(), academicCourse, academicClass);
        saveAgendaDto = new SaveAgendaDto("classwork", "homework", test.getTest());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test field save")
    void testSaveTest() {
        LocalDate date = LocalDate.parse("2022-12-17");
        when(testRepository.save(any(Test.class))).thenReturn(test);
        when(academicClassRepository.findById(saveAgendaDto.getClassId())).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(saveAgendaDto.getCourseId())).thenReturn(Optional.of(academicCourse));
        saveAgendaDto.setDate(date.toString());
        Test actualTest = testService.save(saveAgendaDto);
        Assertions.assertNotNull(actualTest);
        assertEquals(test.getTest(), actualTest.getTest());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test field save with null classId and courseId")
    void testSaveByNullId() {
        AcademicClass aClass = new AcademicClass();
        AcademicCourse aCourse = new AcademicCourse();
        LocalDate date = LocalDate.parse("2022-12-15");
        when(testRepository.save(any(Test.class))).thenReturn(test);
        when(academicClassRepository.findById(aClass.getId())).thenReturn(Optional.of(aClass));
        when(academicCourseRepository.findById(aCourse.getId())).thenReturn(Optional.of(aCourse));
        saveAgendaDto.setDate(date.toString());
        Test actualTest = testService.save(saveAgendaDto);
        assertThat(actualTest.getAcademicClass().getId()).isNotNull();
        assertThat(actualTest.getAcademicCourse().getId()).isNotNull();
    }
}