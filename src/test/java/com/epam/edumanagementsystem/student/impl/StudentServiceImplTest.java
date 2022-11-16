package com.epam.edumanagementsystem.student.impl;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.BloodGroup;
import com.epam.edumanagementsystem.student.model.entity.Gender;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.repository.StudentRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;
import com.epam.edumanagementsystem.util.exceptions.UserNotFoundException;
import com.epam.edumanagementsystem.util.repository.UserRepository;
import com.epam.edumanagementsystem.util.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class StudentServiceImplTest {
    List<Student> studentList = new ArrayList<>();
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private StudentServiceImpl studentService;
    private Student student;
    private User studentDtoUser;
    private Student secondStudent;
    private StudentDto datesToBeUpdated;
    private User userStudent;

    @BeforeEach
    void setUp() {
        userStudent = new User(null, "email@gmail.com", "STUDENT");
        User secondStudentUser = new User(4L, "secondUser@gmail.com", "STUDNET");
        studentDtoUser = new User(5L, "studentDtoUser@gmail.com", "STUDNET");
        User userParent = new User(2L, "parent@gmail.com", "PARENT");
        User userTeacher = new User(3L, "teacher@gmail.com", "TEACHER");
        Parent parent = new Parent(2L, "Parent", "Parentyan", userParent, "6uP&6jV$2qP%");
        Set<Teacher> teacherSet = new LinkedHashSet<>();
        Teacher teacher = new Teacher(3L,
                "Teacher",
                "Surname",
                userTeacher,
                "password",
                new LinkedHashSet<>(),
                new LinkedHashSet<>(),
                new LinkedHashSet<>());
        teacherSet.add(teacher);
        AcademicClass academicClass = new AcademicClass(1L,
                "1A",
                teacherSet,
                new LinkedHashSet<>(),
                teacher,
                new ArrayList<>(),
                new LinkedHashSet<>());
        student = new Student(1L,
                "Student",
                "Surname",
                userStudent,
                "Address",
                LocalDate.ofEpochDay(1L),
                Gender.MALE,
                "password",
                BloodGroup.A_PLUS,
                parent,
                academicClass);
        studentList.add(student);

        secondStudent = new Student(4L, "Student",
                "Surname",
                secondStudentUser,
                "Address",
                LocalDate.ofEpochDay(1L),
                Gender.MALE,
                "password",
                BloodGroup.A_PLUS,
                parent,
                academicClass);
        datesToBeUpdated = new StudentDto(4L,
                "Student1",
                "Studentyan",
                "studentDtoUser@gmail.com",
                "Gyumri",
                LocalDate.ofEpochDay(1L),
                BloodGroup.O_MINUS,
                Gender.MALE,
                "pass",
                parent,
                academicClass);
    }

    @Test
    void testFindAllWhenListIsEmptyNegativeCase() {
        when(studentRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(studentService.findAll().isEmpty());
        verify(studentRepository).findAll();
    }

    @Test
    void testFindAllStudents() {
        studentService.findAll();
        verify(studentRepository).findAll();
    }

    @Test
    void testFindAllPositiveCaseWhenAddedOneStudent() {
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(1, studentService.findAll().size());
        verify(studentRepository).findAll();
    }

    @Test
    void testFindAllPositiveCaseCheckListSizeWhenCreatedTwoStudents() {
        studentList.add(secondStudent);
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(2, studentService.findAll().size());
        verify(studentRepository).findAll();
    }

    @Test
    void canCreateStudent() {
        when(userService.save(any())).thenReturn(userStudent);
        when(studentRepository.save(any())).thenReturn(student);
        StudentDto studentDto = StudentMapper.toStudentDto(student);

        Student createdStudent = studentService.create(studentDto);

        assertThat(createdStudent).isNotNull();
        verify(studentRepository).save(student);
        verify(userService).save(userStudent);
    }

    @Test
    void testCreateNegativeCaseWhenParamsIsNull() {
        assertThrows(ObjectIsNull.class, () -> studentService.create(StudentMapper.toStudentDto(null), userService));
    }

    @Test
    void testUpdate() {
        when(studentRepository.save(any())).thenReturn(student);
        assertSame(student, studentService.updateStudentsClass(secondStudent));
        verify(studentRepository).save(any());
    }

    @Test
    void testUpdateNegativeCase() {
        assertThrows(ObjectIsNull.class, () -> studentService.updateStudentsClass(null));
    }

    @Test
    void testFindByUserId() {
        when(studentRepository.findByUserId(any())).thenReturn(student);
        assertSame(student, studentService.findByUserId(1L));
        verify(studentRepository).findByUserId(any());
    }

    @Test
    void testFindByUserIdNegativeCase() {
        assertThrows(UserNotFoundException.class, () -> studentService.findByUserId((null)));
    }

    @Test
    void testFindByAcademicClassId() {
        ArrayList<Student> studentList = new ArrayList<>();
        when(studentRepository.findByAcademicClassId(any())).thenReturn(studentList);
        List<Student> actualFindByAcademicClassIdResult = studentService.findByAcademicClassId(1L);
        assertSame(studentList, actualFindByAcademicClassIdResult);
        assertTrue(actualFindByAcademicClassIdResult.isEmpty());
        verify(studentRepository).findByAcademicClassId(any());
    }

    @Test
    void testFindByAcademicClassIdNegativeCase() {
        assertThrows(UserNotFoundException.class, () -> studentService.findByAcademicClassId(null));
    }

    @Test
    void testUpdateFieldsPositiveCase() {
        Student student = StudentMapper.toStudent(datesToBeUpdated, studentDtoUser);
        Optional<Student> optionalStudent = Optional.of(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(userRepository.save(studentDtoUser)).thenReturn(studentDtoUser);
        when(studentRepository.findById(4L)).thenReturn(optionalStudent);
        when(userService.findByEmail(datesToBeUpdated.getEmail())).thenReturn(studentDtoUser);

        StudentDto updatedStudent = studentService.updateFields(datesToBeUpdated);

        verify(studentRepository, times(1)).save(student);
        assertEquals(updatedStudent.getId(), datesToBeUpdated.getId());
        assertEquals(updatedStudent.getName(), datesToBeUpdated.getName());
        assertEquals(updatedStudent.getSurname(), datesToBeUpdated.getSurname());
        assertEquals(updatedStudent.getEmail(), datesToBeUpdated.getEmail());
        assertEquals(updatedStudent.getDate(), datesToBeUpdated.getDate());
        assertEquals(updatedStudent.getGender(), datesToBeUpdated.getGender());
        assertEquals(updatedStudent.getPassword(), datesToBeUpdated.getPassword());
        assertEquals(updatedStudent.getBloodGroup(), datesToBeUpdated.getBloodGroup());
        assertEquals(updatedStudent.getAcademicClass(), datesToBeUpdated.getAcademicClass());
        assertEquals(updatedStudent.getParent(), datesToBeUpdated.getParent());
    }

    @Test
    void testUpdateFieldsNegativeCase_studentIsNull() {
        assertThrows(ObjectIsNull.class, () -> studentService.updateFields(null));
    }
}
