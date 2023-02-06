package com.epam.edumanagementsystem.admin.journal_agenda.impl;

import com.epam.edumanagementsystem.admin.journal.model.dto.SaveAgendaDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Classwork;
import com.epam.edumanagementsystem.admin.journal.rest.repository.ClassworkRepository;
import com.epam.edumanagementsystem.admin.journal.rest.service.impl.ClassworkServiceImpl;
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
import java.util.List;
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
    private SaveAgendaDto saveAgendaDto;

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
        classwork = new Classwork(1L, "classwork", LocalDate.now(), academicCourse, academicClass);
        saveAgendaDto = new SaveAgendaDto(classwork.getClasswork(), "homework", "test");
    }

    @Test
    @DisplayName("Classwork field save")
    void testSaveClasswork() {
        LocalDate date = LocalDate.parse("2022-12-17");
        when(classworkRepository.save(any(Classwork.class))).thenReturn(classwork);
        when(academicClassRepository.findById(saveAgendaDto.getClassId())).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(saveAgendaDto.getCourseId())).thenReturn(Optional.of(academicCourse));
        saveAgendaDto.setDate(date.toString());
        Classwork actualClasswork = classworkService.save(saveAgendaDto);
        Assertions.assertNotNull(actualClasswork);
        assertEquals(classwork.getClasswork(), actualClasswork.getClasswork());
    }

    @Test
    @DisplayName("Classwork field save with null classId and courseId")
    void testSaveByNullId() {
        AcademicClass aClass = new AcademicClass();
        AcademicCourse aCourse = new AcademicCourse();
        LocalDate date = LocalDate.parse("2022-12-15");
        when(classworkRepository.save(any(Classwork.class))).thenReturn(classwork);
        when(academicClassRepository.findById(aClass.getId())).thenReturn(Optional.of(academicClass));
        when(academicCourseRepository.findById(aCourse.getId())).thenReturn(Optional.of(academicCourse));
        saveAgendaDto.setDate(date.toString());
        Classwork actualClasswork = classworkService.save(saveAgendaDto);
        assertThat(actualClasswork.getAcademicClass().getId()).isNotNull();
        assertThat(actualClasswork.getAcademicCourse().getId()).isNotNull();
    }
}