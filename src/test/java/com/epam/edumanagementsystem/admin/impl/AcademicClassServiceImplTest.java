package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcademicClassServiceImplTest {
    private AcademicClass academicClass;
    private AcademicCourse academicCourse;
    private Teacher teacher;
    @Mock
    private AcademicClassRepository academicClassRepository;

    @InjectMocks
    private AcademicClassServiceImpl academicClassServiceImpl;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setRole("TEACHER");
        user.setEmail("t@gmail.com");
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherName");
        teacher.setSurname("TeacherSurname");
        teacher.setUser(user);
        teacher.setPassword(user.getEmail());
        teacher.setPassword("password");
        teacher.setAcademicClass(new HashSet<>());
        teacher.setAcademicCourseSet(new HashSet<>());
        academicClass = new AcademicClass();
        academicClass.setId(1L);
        academicClass.setClassNumber("A1");
        academicClass.setClassroomTeacher(teacher);
        academicCourse = new AcademicCourse();
        academicCourse.setName("Course 1");
        Set<AcademicCourse> academicCourseSet = new HashSet<>();
        academicCourseSet.add(academicCourse);
        academicClass.setAcademicCourseSet(academicCourseSet);
        Set<Teacher> allTeacher = new HashSet<>();
        allTeacher.add(teacher);
        academicClass.setTeacher(allTeacher);
        academicClass.setClassroomTeacher(teacher);
    }

    @DisplayName("Create academic class - positive case")
    @Test
    void testCreatePositive() {
        when(academicClassRepository.save(any(AcademicClass.class))).thenReturn(academicClass);
        AcademicClass actualAcademicClass = academicClassServiceImpl.create(academicClass);
        assertEquals(academicClass, actualAcademicClass);
        verify(academicClassRepository, times(1)).save(actualAcademicClass);
    }

    @DisplayName("Create academic class - null case")
    @Test
    void testCreateNull() {
        assertThrows(NullPointerException.class, () -> academicClassServiceImpl.create(null));
    }

    @DisplayName("Get academic class by id - positive case")
    @Test
    void testGetByIdPositive() {
        when(academicClassRepository.findById(1L)).thenReturn(Optional.of(academicClass));
        AcademicClassDto academicClassDto = academicClassServiceImpl.getById(1L);
        assertEquals(1L, academicClassDto.getId());
    }

    @DisplayName("Get academic class by id - null case")
    @Test
    void testGetByIdNull() {
        Long classId = null;
        when(academicClassRepository.findById(any())).thenReturn(Optional.empty());
        AcademicClassDto academicClassDto = academicClassServiceImpl.getById(classId);
        assertEquals(classId, academicClassDto.getId());
    }

    @DisplayName("Find academic class by name or classNumber - positive case")
    @Test
    void testFindByNamePositive() {
        when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(academicClass);
        AcademicClass actualClass = academicClassServiceImpl.findByName(academicClass.getClassNumber());
        assertEquals(academicClass.getClassNumber(), actualClass.getClassNumber());
    }

    @DisplayName("Find academic class by incorrect name - false name case")
    @Test
    void testFindByIncorrectName() {
        when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(academicClass);
        AcademicClass aClass = new AcademicClass(1L, "A3", null, null, null, null, null);
        AcademicClass actualClass = academicClassServiceImpl.findByName(academicClass.getClassNumber());
        assertNotEquals(actualClass.getClassNumber(), aClass.getClassNumber());
    }

    @DisplayName("Update academic class by name or classNumber - positive case")
    @Test
    void testUpdatePositive() {
        Mockito.when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(academicClass);
        Mockito.when(academicClassRepository.save(academicClass)).thenReturn(academicClass);
        AcademicClass academicClass1 = academicClassServiceImpl.update(academicClass);
        academicClassServiceImpl.update(academicClass1);
        assertNotNull(academicClass);
        assertNotNull(academicClass1);
        assertEquals(academicClass.getClassNumber(), academicClass1.getClassNumber());
    }

    @DisplayName("Find all academic courses by academic class name or classNumber - positive case")
    @Test
    void testFindAllAcademicCoursesPositive() {
        Mockito.when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(academicClass);
        List<AcademicCourse> course = academicClassServiceImpl.findAllAcademicCourses(academicClass.getClassNumber());
        course.add(academicCourse);
        assertNotNull(academicClass);
        assertEquals(academicCourse.getName(), course.get(1).getName());
    }

    @DisplayName("Find all teachers by academic class name or classNumber - positive case")
    @Test
    void testFindAllTeachersPositive() {
        when(academicClassServiceImpl.findByName(academicClass.getClassNumber())).thenReturn(academicClass);
        Set<Teacher> teachers = academicClassServiceImpl.findAllTeachers(academicClass.getClassNumber());
        teachers.add(teacher);
        verify(academicClassRepository, times(1)).findByClassNumber(academicClass.getClassNumber());
        assertEquals(1, teachers.size());
    }

    @DisplayName("Find all teachers by List<AcademicClass> findAll() - positive case")
    @Test
    void testFindAllTeacher() {
        Set<Teacher> teachersByAcademicClass = new HashSet<>();
        Set<Teacher> result = academicClass.getTeacher();
        teachersByAcademicClass.addAll(result);
        when(academicClassRepository.findAll()).thenReturn(List.of(academicClass));
        teachersByAcademicClass = academicClassServiceImpl.findAllTeacher();
        assertNotNull(academicClass);
        assertEquals(academicClass.getTeacher().size(), teachersByAcademicClass.size());
    }

    @DisplayName("Find all academic class dto - positive case")
    @Test
    void testFindAllPositive() {
        when(academicClassRepository.findAll()).thenReturn(List.of(new AcademicClass()));
        List<AcademicClassDto> academicClassDtoList = academicClassServiceImpl.findAll();
        assertNotNull(academicClassDtoList);
        assertEquals(1, academicClassDtoList.size());
    }
}