package com.epam.edumanagementsystem.admin.impl;

import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
import com.epam.edumanagementsystem.admin.rest.service.impl.AcademicClassServiceImpl;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        AcademicClass actualAcademicClass = academicClassServiceImpl.save(academicClass);
        assertEquals(academicClass, actualAcademicClass);
        verify(academicClassRepository, times(1)).save(actualAcademicClass);
    }

    @DisplayName("Create academic class - null case")
    @Test
    void testCreateNull() {
        assertThrows(NullPointerException.class, () -> academicClassServiceImpl.save(null));
    }

    @DisplayName("Get academic class by id - positive case")
    @Test
    void testGetByIdPositive() {
        when(academicClassRepository.findById(1L)).thenReturn(Optional.of(academicClass));
        AcademicClassDto academicClassDto = academicClassServiceImpl.findById(1L);
        assertEquals(1L, academicClassDto.getId());
    }

    @DisplayName("Get academic class by id - null case")
    @Test
    void testGetByIdNull() {
        Long classId = null;
        when(academicClassRepository.findById(any())).thenReturn(Optional.empty());
        AcademicClassDto academicClassDto = academicClassServiceImpl.findById(classId);
        assertEquals(classId, academicClassDto.getId());
    }

    @DisplayName("Find academic class by name or classNumber - positive case")
    @Test
    void testFindByNamePositive() {
        when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(academicClass);
        AcademicClass actualClass = academicClassServiceImpl.findByClassNumber(academicClass.getClassNumber());
        assertEquals(academicClass.getClassNumber(), actualClass.getClassNumber());
    }

    @DisplayName("Find academic class by incorrect name - false name case")
    @Test
    void testFindByIncorrectName() {
        when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(academicClass);
        AcademicClass aClass = new AcademicClass(1L, "A3", null, null, null, null, null);
        AcademicClass actualClass = academicClassServiceImpl.findByClassNumber(academicClass.getClassNumber());
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

    @DisplayName("Find all academic class dto - positive case")
    @Test
    void testFindAllPositive() {
        when(academicClassRepository.findAll()).thenReturn(List.of(new AcademicClass()));
        List<AcademicClassDto> academicClassDtoList = academicClassServiceImpl.findAll();
        assertNotNull(academicClassDtoList);
        assertEquals(1, academicClassDtoList.size());
    }

    @Test
    @DisplayName("Find all academic classes when linked to the classes in teacher profile")
    void findAcademicClassByTeacherId() {
        Set<AcademicClass> classSet = new HashSet<>();
        classSet.add(academicClass);
        when(academicClassRepository.findAcademicClassByTeacherId(any())).thenReturn(classSet);
        Set<AcademicClass> classes = academicClassServiceImpl.findByTeacherId(teacher.getId());
        assertNotNull(classSet);
        assertTrue(classes.containsAll(classSet));
    }
}