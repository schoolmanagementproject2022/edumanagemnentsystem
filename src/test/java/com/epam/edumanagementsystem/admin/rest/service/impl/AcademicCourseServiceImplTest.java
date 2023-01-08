package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicCourseMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicCourseDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.model.entity.Subject;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicCourseRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.Mockito.when;

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
        academicCourse = new AcademicCourse(1L, "AcademicCourseName",
                subject, teacherSet, academicClasses);
        coursesSet = new LinkedHashSet<>();
        coursesSet.add(academicCourse);

    }

    @Test
    @DisplayName("should find academic course by given academic name")
    void testFindAcademicCourseByAcademicCourseName() {
        String name = "firstAcademicCourse";
        when(academicCourseRepository.findByName(name)).thenReturn(Optional.ofNullable(academicCourse));
        AcademicCourse academicCourseByAcademicCourseName =
                academicCourseService.findByName(name);
        Assertions.assertNotNull(academicCourseByAcademicCourseName);
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
    @DisplayName("should find academic course by given id")
    void testFindById() {
        long id = 1L;
        when(academicCourseRepository.findById(id)).thenReturn(Optional.ofNullable(academicCourse));
        AcademicCourseDto academicCourseById = academicCourseService.findById(id);
        Assertions.assertNotNull(academicCourseById);
        assertEquals(id, academicCourseById.getId());
    }

    @Test
    @DisplayName("should not find academic course by given absent id")
    void testFindByIdFail() {
        long id = 8L;
        assertThrows(RuntimeException.class, () -> academicCourseService.findById(id));
    }

    @Test
    @DisplayName("should create academic course success")
    void testCreateSuccess() {
        when(academicCourseRepository.save(academicCourse)).thenReturn(academicCourse);
        AcademicCourseDto academicCourseDto = AcademicCourseMapper.toDto(academicCourse);
        AcademicCourseDto actualAcademicCourse = academicCourseService.save(AcademicCourseMapper.toDto(academicCourse));
        assertEquals(academicCourseDto, actualAcademicCourse);
    }

    @Test
    @DisplayName("should throw NullPointerException")
    void testCreateFail() {
        assertThrows(NullPointerException.class, () -> academicCourseService.save(null));
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
        academicCourse.setTeachers(teachersInAcademicCourse);
        assertEquals(teachersInAcademicCourse, academicCourse.getTeachers());
        assertEquals(1, teachersInAcademicCourse.size());
    }

    @Test
    @DisplayName("should update academic course")
    void testUpdateSuccess() {
        Set<Teacher> teacherSet2 = new HashSet<>();
        Set<AcademicClass> academicClasses2 = new HashSet<>();
        Subject subject2 = new Subject();
        AcademicCourse academicCourseForUpdate = new AcademicCourse(1L, "secondName", subject2, teacherSet2, academicClasses2);
        when(academicCourseRepository.findByName("secondName")).thenReturn(Optional.of(academicCourseForUpdate));
        when(academicCourseRepository.save(academicCourseForUpdate)).thenReturn(academicCourseForUpdate);
        AcademicCourseDto updatedAcademicCourse = academicCourseService.update(AcademicCourseMapper.toDto(academicCourseForUpdate));
        assertEquals("secondName", updatedAcademicCourse.getName());
    }

    @Test
    @DisplayName("should update academic course")
    void testUpdateFail() {
        assertThrows(NullPointerException.class, () -> academicCourseService.update(null));
    }

    @Test
    void testFindAcademicCoursesByTeacherId() {
        when(academicCourseRepository.findAllByTeachersId(1L)).thenReturn(coursesSet);
        Set<AcademicCourseDto> academicCoursesByTeacherId = academicCourseService.findAllByTeachersId(1L);
        Set<AcademicCourseDto> academicCourseDto = AcademicCourseMapper.toSetOfAcademicCourseDto(coursesSet);
        academicCourseService.findAllByTeachersId(1L);
        assertEquals(academicCoursesByTeacherId, academicCourseDto);
        assertThat(coursesSet).isNotNull();
    }

}