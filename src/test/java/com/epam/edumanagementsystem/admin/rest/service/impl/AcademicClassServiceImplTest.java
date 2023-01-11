package com.epam.edumanagementsystem.admin.rest.service.impl;

import com.epam.edumanagementsystem.admin.mapper.AcademicClassMapper;
import com.epam.edumanagementsystem.admin.model.dto.AcademicClassDto;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.admin.rest.repository.AcademicClassRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        academicClass.setTeachers(allTeacher);
        academicClass.setClassroomTeacher(teacher);
    }

    @DisplayName("Save academic class")
    @Test
    void testSaveAcademicClass() {
        when(academicClassRepository.save(any(AcademicClass.class))).thenReturn(academicClass);
        AcademicClassDto actualAcademicClass = academicClassServiceImpl.save(AcademicClassMapper.toDto(academicClass));
        Assertions.assertNotNull(actualAcademicClass);
        assertThat(actualAcademicClass.getId()).isPositive();
    }

    @DisplayName("Find academic class by id")
    @Test
    void testFindByIdPositive() {
        when(academicClassRepository.findById(1L)).thenReturn(Optional.of(academicClass));
        AcademicClassDto academicClassDto = academicClassServiceImpl.findById(1L);
        assertEquals(1L, academicClassDto.getId());
    }

    @DisplayName("Not found academic class by given id")
    @Test
    void testFindByIdNotFound() {
        assertThrows(EntityNotFoundException.class, () -> academicClassServiceImpl.findById(3L));
    }

    @DisplayName("Find academic class by name or classNumber")
    @Test
    void testFindByClassNumberPositive() {
        when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(Optional.ofNullable(academicClass));
        AcademicClass actualClass = academicClassServiceImpl.findByClassNumber(academicClass.getClassNumber());
        assertEquals(academicClass.getClassNumber(), actualClass.getClassNumber());
    }

    @DisplayName("Find academic class by incorrect name")
    @Test
    void testFindByIncorrectClassNumber() {
        when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(Optional.ofNullable(academicClass));
        AcademicClass aClass = new AcademicClass(1L, "A3", null, null, null, null, null);
        AcademicClass actualClass = academicClassServiceImpl.findByClassNumber(academicClass.getClassNumber());
        assertNotEquals(actualClass.getClassNumber(), aClass.getClassNumber());
    }

    @DisplayName("Update academic class by name or classNumber - positive case")
    @Test
    void testUpdatePositive() {
//        Mockito.when(academicClassRepository.findByClassNumber(academicClass.getClassNumber())).thenReturn(academicClass);
//        Mockito.when(academicClassRepository.save(academicClass)).thenReturn(academicClass);
//        AcademicClass academicClass1 = academicClassServiceImpl.update(academicClass);
//        academicClassServiceImpl.update(academicClass1);
//        assertNotNull(academicClass);
//        assertNotNull(academicClass1);
//        assertEquals(academicClass.getClassNumber(), academicClass1.getClassNumber());
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
    void testFindByTeacherIdFound() {
        when(academicClassRepository.findAcademicClassByTeachersId(1L)).thenReturn(Set.of(new AcademicClass("A1")));

        Set<AcademicClassDto> foundClasses = academicClassServiceImpl.findByTeacherId(1L);
        assertEquals(1, foundClasses.size());
        assertEquals(academicClass.getClassNumber(), foundClasses.iterator().next().getClassNumber());
    }

    @Test
    void testFindByTeacherIdNotFound() {
        when(academicClassRepository.findAcademicClassByTeachersId(1L)).thenReturn(Set.of());
        Set<AcademicClassDto> foundClasses = academicClassServiceImpl.findByTeacherId(1L);
        assertEquals(0, foundClasses.size());
    }


    @Test
    void testRemoveByTeacherNameClassFound() {
        when(academicClassRepository.removeByTeacherName("TeacherName")).thenReturn(academicClass);
        AcademicClass removedClass = academicClassServiceImpl.removeByTeacherName("TeacherName");
        assertEquals("A1", removedClass.getClassNumber());
        assertEquals(academicClass.getTeachers(), removedClass.getTeachers());
    }

    @Test
    void testRemoveByTeacherNameClassNotFound() {
        when(academicClassRepository.removeByTeacherName("TeacherName")).thenReturn(null);
        AcademicClass removedClass = academicClassServiceImpl.removeByTeacherName("TeacherName");
        assertNull(removedClass);
    }

}