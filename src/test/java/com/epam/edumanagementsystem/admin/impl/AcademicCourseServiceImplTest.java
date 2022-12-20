package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcademicCourseServiceImplTest {

    private AcademicCourse academicCourse;
    private Teacher teacher;
    private Subject subject;

    @Mock
    private AcademicCourseRepository academicCourseRepository;

    @InjectMocks
    private AcademicCourseServiceImpl academicCourseService;
    private Set<AcademicCourse> coursesSet;

    @BeforeEach
    void init() {
        User user = new User(null, "gm@gmail.com", "TEACHER");
        Set<Teacher> teacherSet = new HashSet<>();
        Set<AcademicClass> academicClasses = new HashSet<>();
        academicCourse = new AcademicCourse(1L, "firstAcademicCourse",
                subject, teacherSet, academicClasses);
        coursesSet = new LinkedHashSet<>();
        coursesSet.add(academicCourse);

    }

    @Test
    @DisplayName("should find academic course by given academic name")
    void testFindAcademicCourseByAcademicCourseName() {
        String name = "firstAcademicCourse";
        when(academicCourseRepository.findAcademicCourseByName(name)).thenReturn(academicCourse);
        AcademicCourse academicCourseByAcademicCourseName =
                academicCourseService.findByName(name);
        assertEquals(academicCourse, academicCourseByAcademicCourseName);
    }

    @Test
    @DisplayName("should not find academic course by given wrong academic name")
    void testFindAcademicCourseByAcademicCourseNameFail() {
        String name = "wrongName";
        assertThrows(RuntimeException.class, () -> academicCourseService
                .findByName(name));
    }

    @Test
    @DisplayName("should throw NulPointerException")
    void testFindAcademicCourseByNullFail() {
        assertThrows(NullPointerException.class, () -> academicCourseService
                .findByName(null));
    }

    @Test
    @DisplayName("should find academic course by given id")
    void testFindById() {
        long id = 1L;
        when(academicCourseRepository.findAcademicCourseById(id)).thenReturn(academicCourse);
        AcademicCourse academicCourseById = academicCourseService.findByID(id);
        assertEquals(academicCourse, academicCourseById);
    }

    @Test
    @DisplayName("should not find academic course by given absent id")
    void testFindByIdFail() {
        long id = 8L;
        assertThrows(RuntimeException.class, () -> academicCourseService.findByID(id));
    }

    @Test
    @DisplayName("should not find academic course by null id")
    void testFindByNullIdFail() {
        assertThrows(NullPointerException.class, () -> academicCourseService.findByID(null));
    }

    @Test
    @DisplayName("should create academic course success")
    void testCreateSuccess() {
        when(academicCourseRepository.save(academicCourse)).thenReturn(academicCourse);
        AcademicCourseDto academicCourseDto = AcademicCourseMapper.toDto(academicCourse);
        AcademicCourseDto actualAcademicCourse = academicCourseService.create(academicCourse);
        assertEquals(academicCourseDto, actualAcademicCourse);
    }

    @Test
    @DisplayName("should throw NullPointerException")
    void testCreateFail() {
        assertThrows(NullPointerException.class, () -> academicCourseService.create(null));
    }

    @Test
    @DisplayName("should find all academic course Dto")
    void testFindAllAcademicCourseDto() {
        List<AcademicCourse> academicCourseList = new ArrayList<>();
        academicCourseList.add(academicCourse);
        when(academicCourseRepository.findAll()).thenReturn(academicCourseList);
        List<AcademicCourseDto> allAcademicCourseDtoList = AcademicCourseMapper.toListOfAcademicCourseDto(academicCourseList);
        List<AcademicCourseDto> allActualAcademicCourseDtos = academicCourseService.findAll();
        assertEquals(allAcademicCourseDtoList, allActualAcademicCourseDtos);
        assertEquals(1, allActualAcademicCourseDtos.size());
    }

    @Test
    @DisplayName("should find all teachers in academic courses")
    void testFindAllTeachersInAcademicCourses() {
        Set<Teacher> teachersInAcademicCourse = new HashSet<>();
        teachersInAcademicCourse.add(teacher);
        academicCourse.setTeacher(teachersInAcademicCourse);
        assertEquals(teachersInAcademicCourse, academicCourse.getTeacher());
        assertEquals(1, teachersInAcademicCourse.size());
    }

    @Test
    @DisplayName("should update academic course")
    void testUpdateSuccess() {
        Set<Teacher> teacherSet2 = new HashSet<>();
        Set<AcademicClass> academicClasses2 = new HashSet<>();
        Subject subject2 = new Subject();
        AcademicCourse academicCourseForUpdate = new AcademicCourse(1L, "secondName", subject2, teacherSet2, academicClasses2);
        when(academicCourseRepository.findAcademicCourseByName("secondName")).thenReturn(academicCourseForUpdate);
        when(academicCourseRepository.save(academicCourseForUpdate)).thenReturn(academicCourseForUpdate);
        AcademicCourseDto updatedAcademicCourse = academicCourseService.update(academicCourseForUpdate);
        assertEquals("secondName", updatedAcademicCourse.getName());
    }

    @Test
    @DisplayName("should update academic course")
    void testUpdateFail() {
        assertThrows(NullPointerException.class, () -> academicCourseService.update(null));
    }

    @Test
    void findAcademicCoursesByTeacherId() {
        when(academicCourseRepository.findAcademicCoursesByTeacherId(1L)).thenReturn(coursesSet);
        Set<AcademicCourseDto> academicCoursesByTeacherId = academicCourseService.findAcademicCoursesByTeacherId(1L);
        Set<AcademicCourseDto> academicCourseDto = AcademicCourseMapper.toSetOfAcademicCourseDto(coursesSet);
        academicCourseService.findAcademicCoursesByTeacherId(1L);
        assertEquals(academicCoursesByTeacherId, academicCourseDto);
        assertThat(coursesSet).isNotNull();
    }
}