package com.epam.edumanagementsystem.admin.timetable.impl;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.TimetableRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimetableServiceImplTest {
    @Mock
    private TimetableRepository timetableRepository;

    @InjectMocks
    private TimetableServiceImpl timetableService;

    private Timetable timetable;

    private AcademicClass academicClass;


    @BeforeEach
    void setUp() {
        Teacher teacher = new Teacher(1L, "Teacher", "Teacheryan", new User(), "25g*h$+52",
                new HashSet(), new HashSet(), new HashSet());
        academicClass = new AcademicClass(1L, "5A", new HashSet(), new HashSet(),
                teacher, new ArrayList(), new HashSet());

        timetable = new Timetable(1L, LocalDate.now(),
                LocalDate.of(2022, 11, 12), "Active", academicClass);
    }

    @Test
    @DisplayName("Save timetable with all fields")
    void canCreateTimetable() {
        when(timetableRepository.save(any())).thenReturn(timetable);

        TimetableDto createdTimetable = timetableService.create(timetable);

        assertThat(createdTimetable).isNotNull();
        verify(timetableRepository, times(1)).save(timetable);
    }

    @Test
    @DisplayName("Verify usage of findAll methode")
    void usageOfFindAll() {
        timetableService.findAll();
        verify(timetableRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("The methode return  the right size of objects")
    void findAll_returnRightObjects() {
        when(timetableRepository.findAll()).thenReturn(List.of(timetable, timetable));
        List<Timetable> allTimetables = timetableService.findAll();

        assertThat(allTimetables).isNotNull();
        assertThat(allTimetables.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("The methode return right object by existing class name")
    void findTimetableByAcademicClassName_returnRightObject_ifClassExist() {
        String className = academicClass.getClassNumber();
        when(timetableRepository.findByAcademicClass_ClassNumber(className)).thenReturn(timetable);

        Timetable timetableByClassName = timetableRepository.findByAcademicClass_ClassNumber(className);
        assertThat(timetableByClassName.getAcademicClass().getClassNumber()).isEqualTo(className);
    }

    @Test
    @DisplayName("The object is not null if you give existing class name")
    void findTimetableByAcademicClassName_isNotNull() {
        String className = academicClass.getClassNumber();
        when(timetableRepository.findByAcademicClass_ClassNumber(className)).thenReturn(timetable);

        Timetable timetableByClassName = timetableRepository.findByAcademicClass_ClassNumber(className);
        assertThat(timetableByClassName.getAcademicClass().getClassNumber()).isNotNull();
    }

    @Test
    @DisplayName("Verify usage of findTimetableByAcademicClassName()")
    void usageOf_findTimetableByAcademicClassName() {
        String classNumber = academicClass.getClassNumber();
        timetableService.findTimetableByAcademicClassName(classNumber);

        verify(timetableRepository, times(1)).findByAcademicClass_ClassNumber(classNumber);
    }

    @Test
    @DisplayName("The methode return right object by existing class id")
    void findTimetableByAcademicClassId_returnRightObject() {
        Long classId = academicClass.getId();
        when(timetableRepository.getTimetableByAcademicClassId(classId)).thenReturn(timetable);

        Timetable timetableByAcademicClassId = timetableService.findTimetableByAcademicClassId(classId);

        assertThat(timetableByAcademicClassId.getAcademicClass()).isEqualTo(academicClass);
        assertThat(timetableByAcademicClassId).isEqualTo(timetable);
    }

    @Test
    @DisplayName("The object is not null if you give existing class id")
    void findTimetableByAcademicClassId_isNotNull() {
        Long classId = academicClass.getId();
        when(timetableRepository.getTimetableByAcademicClassId(classId)).thenReturn(timetable);

        Timetable timetableByAcademicClassId = timetableService.findTimetableByAcademicClassId(classId);
        assertThat(timetableByAcademicClassId).isNotNull();
    }

    @Test
    @DisplayName("Verify usage of updateTimetableStatusByAcademicClassId()")
    void usageOf_updateTimetableStatusByAcademicClassId() {
        timetableService.updateTimetableStatusByAcademicClassId(timetable.getStatus(), academicClass.getId());
        verify(timetableRepository, times(1)).updateTimetableStatusByAcademicClassId(timetable.getStatus(),
                academicClass.getId());
    }

    @Test
    @DisplayName("Verify usage of updateTimetableDatesAndStatusByAcademicClassId()")
    void usageOf_updateTimetableDatesAndStatusByAcademicClassId() {
        timetableService.updateTimetableDatesAndStatusByAcademicClassId(LocalDate.now(),
                LocalDate.of(2024, 12, 29), "Active", academicClass.getId());
        verify(timetableRepository, times(1)).updateTimetableDatesAndStatusByAcademicClassId(LocalDate.now(),
                LocalDate.of(2024, 12, 29), "Active", academicClass.getId());
    }
}