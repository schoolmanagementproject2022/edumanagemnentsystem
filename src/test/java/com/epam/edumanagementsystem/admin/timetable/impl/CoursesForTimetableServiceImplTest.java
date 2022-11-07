package com.epam.edumanagementsystem.admin.timetable.impl;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.admin.timetable.rest.repository.CoursesForTimetableRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoursesForTimetableServiceImplTest {

    private CoursesForTimetable course;
    private CoursesForTimetable editedCourse;
    private CoursesForTimetable nonActiveCourse;


    @Mock
    private CoursesForTimetableRepository coursesRepository;

    @InjectMocks
    private CoursesForTimetableServiceImpl coursesForTimetableService;

    @BeforeEach
    void setup() {
        Teacher teacher = new Teacher(1L, "John", "Doe", new User(), "password");
        Subject subject = new Subject(1L, "English", Set.of(teacher));
        AcademicClass academicClass = new AcademicClass(1L, "8A", new HashSet(), new HashSet(), teacher, new ArrayList(), new HashSet());
        AcademicCourse academicCourse = new AcademicCourse(1L, "English", subject, Set.of(teacher), Set.of(academicClass));

        course = new CoursesForTimetable(1L, academicCourse.getName(), List.of(academicClass), "MONDAY", "ACTIVE");
        editedCourse = new CoursesForTimetable(2L, academicCourse.getName(), List.of(academicClass), "TUESDAY", "EDIT");
        nonActiveCourse = new CoursesForTimetable(2L, academicCourse.getName(), List.of(academicClass), "TUESDAY", "NOT ACTIVE");
    }

    @Test
    public void testCreateIsOK() {
        String dayOfWeek = "MONDAY";
        String academicClassName = "English";
        Long academicClassId = 1L;
        String status = "ACTIVE";
        doNothing().when(coursesRepository).create(dayOfWeek, academicClassName, academicClassId, status);

        coursesForTimetableService.create(course);

        verify(coursesRepository, times(1)).create(dayOfWeek, academicClassName, academicClassId, status);

    }

    @Test
    public void testDeleteByIdIsOK() {
        Long id = 1L;

        coursesForTimetableService.deleteCourseById(id);

        Optional<CoursesForTimetable> optionalCoursesForTimetable = coursesRepository.findById(id);
        assertTrue(optionalCoursesForTimetable.isEmpty());
    }

    @Test
    public void testUpdateCourseStatusByIdChangesStatusToNotActive() {
        Long id = 1L;
        doNothing().when(coursesRepository).updateCourseStatusById(id);

        coursesForTimetableService.updateCourseStatusById(id);

        verify(coursesRepository, times(1)).updateCourseStatusById(id);
    }

    @Test
    public void testGetCoursesByAcademicClassIdReturnsListOfCourses() {
        Long id = 1L;

        when(coursesRepository.findCoursesByAcademicClassId(id)).thenReturn(List.of(course));

        List<CoursesForTimetable> listOfCourses = coursesForTimetableService.getCoursesByAcademicClassId(id);
        assertNotNull(listOfCourses);
        assertEquals(1, listOfCourses.size());
    }

    @Test
    public void testGetCoursesForDayAndAcademicClassIdReturnsMondayCourses() {
        String dayOfWeek = "MONDAY";
        Long academicClassId = 1L;
        when(coursesRepository.findCoursesByDayOfWeekAndAcademicClassId(dayOfWeek, academicClassId))
                .thenReturn(List.of(course));

        List<CoursesForTimetable> listOfCourses = coursesForTimetableService.getCoursesForDayAndAcademicClassId(dayOfWeek, academicClassId);

        assertNotNull(listOfCourses);
        assertEquals(1, listOfCourses.size());
        assertThat(listOfCourses.get(0).getDayOfWeek().equalsIgnoreCase(dayOfWeek) &&
                listOfCourses.get(0).getAcademicClass().get(0).getId().equals(academicClassId));
    }

    @Test
    public void testGetCoursesWithEditStatusByAcademicCourseIdReturnsEditedCourses() {
        String status = "EDIT";
        Long academicClassId = 2L;
        when(coursesRepository.findCoursesWithEditStatusByAcademicCourseId(academicClassId))
                .thenReturn(List.of(editedCourse));

        List<CoursesForTimetable> listOfCourses = coursesForTimetableService.getCoursesWithEditStatusByAcademicCourseId(academicClassId);

        assertNotNull(listOfCourses);
        assertEquals(1, listOfCourses.size());
        assertThat(listOfCourses.get(0).getStatus().equalsIgnoreCase(status));
    }

    @Test
    public void testGetCoursesWithActiveStatusByAcademicCourseIdReturnsActiveCourses() {
        String status = "ACTIVE";
        Long academicClassId = 1L;
        when(coursesRepository.findCoursesWithActiveStatusByAcademicCourseId(academicClassId))
                .thenReturn(List.of(course));

        List<CoursesForTimetable> listOfCourses = coursesForTimetableService.getCoursesWithActiveStatusByAcademicCourseId(academicClassId);

        assertNotNull(listOfCourses);
        assertEquals(1, listOfCourses.size());
        assertThat(listOfCourses.get(0).getStatus().equalsIgnoreCase(status));
    }

    @Test
    public void testGetCoursesWithNotActiveStatusByAcademicCourseIdReturnsListOfNonActiveCourses() {
        String status = "NOT ACTIVE";
        Long academicClassId = 3L;
        when(coursesRepository.findCoursesWithNotActiveStatusByAcademicCourseId(academicClassId))
                .thenReturn(List.of(nonActiveCourse));

        List<CoursesForTimetable> listOfCourses = coursesForTimetableService.getCoursesWithNotActiveStatusByAcademicCourseId(academicClassId);

        assertNotNull(listOfCourses);
        assertEquals(1, listOfCourses.size());
        assertThat(listOfCourses.get(0).getStatus().equalsIgnoreCase(status));
    }

    @Test
    public void testGetCoursesByDayOfWeekAndStatusAndAcademicClassId() {
        String dayOfWeek = "MONDAY";
        String status = "ACTIVE";
        Long academicClassId = 1L;
        when(coursesRepository.findCoursesByDayOfWeekAndStatusAndAcademicClassId(dayOfWeek, status, academicClassId))
                .thenReturn(List.of(course));

        List<CoursesForTimetable> listOfCourses = coursesForTimetableService.getCoursesByDayOfWeekAndStatusAndAcademicClassId(dayOfWeek, status, academicClassId);

        assertNotNull(listOfCourses);
        assertEquals(1, listOfCourses.size());
        assertThat(listOfCourses.get(0).getDayOfWeek().equalsIgnoreCase(dayOfWeek) &&
                listOfCourses.get(0).getStatus().equalsIgnoreCase(status) &&
                listOfCourses.get(0).getAcademicClass().get(0).getId().equals(academicClassId));

    }

    @Test
    public void testIsPresentCoursesForClass() {
        when(coursesRepository.existsCoursesForTimetableByAcademicClass_Id(course.getId())).thenReturn(true);
        boolean result = coursesForTimetableService.isPresentCoursesForClass(course.getId());
        assertTrue(result);
    }

    @Test
    public void testUpdateCourseStatusByIdChangesStatusToActive() {
        Long id = nonActiveCourse.getId();
        doNothing().when(coursesRepository).updateCourseStatusToActiveById(id);

        coursesForTimetableService.updateCourseStatusToActiveById(id);

        verify(coursesRepository, times(1)).updateCourseStatusToActiveById(id);
    }
}