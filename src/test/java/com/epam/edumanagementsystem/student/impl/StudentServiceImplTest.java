package com.epam.edumanagementsystem.student.impl;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.exception.EntityNotFoundException;
import com.epam.edumanagementsystem.parent.model.entity.Parent;
import com.epam.edumanagementsystem.student.mapper.StudentMapper;
import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.BloodGroup;
import com.epam.edumanagementsystem.student.model.entity.Gender;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.student.rest.repository.StudentRepository;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.imageUtil.rest.service.ImageService;
import com.epam.edumanagementsystem.util.repository.UserRepository;
import com.epam.edumanagementsystem.util.service.UserService;
import com.epam.edumanagementsystem.util.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    AcademicClass academicClass;
    Parent parent;
    List<Student> studentList = new ArrayList<>();
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageService imageService;
    @InjectMocks
    private StudentServiceImpl service;
    @InjectMocks
    private UserService userServiceImpl = Mockito.spy(new UserServiceImpl());
    private Student student;
    private User studentDtoUser;
    private Student secondStudent;
    private Student studentWithoutClass;
    private Student studentWithoutParent;
    private StudentDto datesToBeUpdated;
    private User userStudent;

    @BeforeEach
    void setUp() {
        userStudent = new User(null, "email@gmail.com", "STUDENT");
        User secondStudentUser = new User(4L, "secondUser@gmail.com", "STUDENT");
        studentDtoUser = new User(5L, "studentDtoUser@gmail.com", "STUDENT");
        User userParent = new User(2L, "parent@gmail.com", "PARENT");
        User userTeacher = new User(3L, "teacher@gmail.com", "TEACHER");
        parent = new Parent(2L, "Parent", "Parentyan", userParent, "6uP&6jV$2qP%");
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
        academicClass = new AcademicClass(1L,
                "1A",
                teacherSet,
                new LinkedHashSet<>(),
                teacher,
                new ArrayList<>(),
                new LinkedHashSet<>());
        studentWithoutParent = new Student();
        studentWithoutParent.setId(1L);
        studentWithoutParent.setName("Name");
        studentWithoutParent.setSurname("Surname");
//        studentDto.setPicUrl();
        studentWithoutParent.setAcademicClass(academicClass);
        studentWithoutParent.setGender(Gender.MALE);
        studentWithoutParent.setDate(LocalDate.ofEpochDay(1L));
        studentWithoutParent.setAddress("address");
        studentWithoutParent.setBloodGroup(BloodGroup.A_PLUS);
        studentWithoutParent.setUser(userStudent);
        student = new Student();
        student.setId(1L);
        student.setName("Name");
        student.setSurname("Surname");
//        studentDto.setPicUrl();
        student.setAcademicClass(academicClass);
        student.setParent(parent);
        student.setGender(Gender.MALE);
        student.setDate(LocalDate.ofEpochDay(1L));
        student.setAddress("address");
        student.setBloodGroup(BloodGroup.A_PLUS);
        student.setUser(userStudent);
        studentWithoutClass = new Student();
        studentWithoutClass.setId(1L);
        studentWithoutClass.setName("Name");
        studentWithoutClass.setSurname("Surname");
//        studentDto.setPicUrl();
        studentWithoutClass.setParent(parent);
        studentWithoutClass.setGender(Gender.MALE);
        studentWithoutClass.setDate(LocalDate.ofEpochDay(1L));
        studentWithoutClass.setAddress("address");
        studentWithoutClass.setBloodGroup(BloodGroup.A_PLUS);
        studentWithoutClass.setUser(userStudent);
        secondStudent = new Student();
        secondStudent.setId(1L);
        secondStudent.setName("Name");
        secondStudent.setSurname("Surname");
//        secondStudent.setPicUrl();
        secondStudent.setAcademicClass(academicClass);
        secondStudent.setParent(parent);
        secondStudent.setGender(Gender.MALE);
        secondStudent.setDate(LocalDate.ofEpochDay(1L));
        secondStudent.setAddress("address");
        secondStudent.setBloodGroup(BloodGroup.A_PLUS);
        secondStudent.setUser(userStudent);

        datesToBeUpdated = new StudentDto();
        datesToBeUpdated.setId(2L);
        datesToBeUpdated.setName("Name2");
        datesToBeUpdated.setSurname("Surname2");
//        secondStudent.setPicUrl();
        datesToBeUpdated.setAcademicClass(academicClass);
        datesToBeUpdated.setParent(parent);
        datesToBeUpdated.setGender(Gender.MALE);
        datesToBeUpdated.setDate(LocalDate.ofEpochDay(1L));
        datesToBeUpdated.setAddress("address");
        datesToBeUpdated.setBloodGroup(BloodGroup.A_PLUS);
        datesToBeUpdated.setEmail("email@gmail.com");


    }

    @Test
    void findAll() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<StudentDto> all = service.findAll();
        assertEquals(1, all.size());

        verify(studentRepository, times(1)).findAll();
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void testFindAllWhenListIsEmptyNegativeCase() {
        when(studentRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(service.findAll().isEmpty());
        verify(studentRepository).findAll();
    }

    @Test
    void testFindAllPositiveCaseWhenAddedOneStudent() {
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(0, service.findAll().size());
        verify(studentRepository).findAll();
    }

    @Test
    void testFindAllPositiveCaseCheckListSizeWhenCreatedTwoStudents() {
        studentList.add(secondStudent);
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(1, service.findAll().size());
        verify(studentRepository).findAll();
    }

    @Test
    void create() {
        lenient().when(userRepository.save(userStudent)).thenReturn(userStudent);
        when(studentRepository.save(any())).thenReturn(student);
        StudentDto studentDto = StudentMapper.toStudentDto(student);

        StudentDto createdStudent = service.create(studentDto);

        assertThat(createdStudent).isNotNull();
    }


    @Test
    void testUpdate() {
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any())).thenReturn(student);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userStudent));
        StudentDto studentDto = service.updateFields(datesToBeUpdated);
        Student student1 = StudentMapper.toStudent(studentDto, userStudent);
        assertEquals(student, student1);
        verify(studentRepository).save(any());
    }

    @Test
    void testUpdateNegativeCase() {
        assertThrows(EntityNotFoundException.class, () -> service.updateFields(datesToBeUpdated));
    }

    @Test
    void findByUserId() {
        when(studentRepository.findByUserId(any())).thenReturn(student);
        StudentDto byUserId = service.findByUserId(1L);
        Student student1 = StudentMapper.toStudent(byUserId, userStudent);
        assertEquals(student, student1);
        verify(studentRepository).findByUserId(any());
    }

    @Test
    void findByAcademicClassId() {
        when(studentRepository.findByAcademicClassId(any())).thenReturn(List.of(student));

        List<Student> students = service.findByAcademicClassId(academicClass.getId());

        Assertions.assertThat(students).isNotNull();
        assertEquals(List.of(student), students);
    }

    @Test
    void findById() {
        when(studentRepository.findById(any())).thenReturn(Optional.ofNullable(student));
        StudentDto studentDto1 = StudentMapper.toStudentDto(student);
        // test method
        StudentDto studentDto = service.findById(studentDto1.getId());

        // assertions
        assertEquals(studentDto.getId(), student.getId());

        // verifies
        verify(studentRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void findStudentsByParentId() {
        when(studentRepository.findAllByParentId(datesToBeUpdated.getId())).thenReturn(List.of(student));
        List<StudentDto> listOfStudents = service.findStudentsByParentId(parent.getId());
        Assertions.assertThat(listOfStudents).isNotNull();
    }

    @Test
    void findStudentsWithoutParent() {
        when(studentRepository.findAllByParentIsNull()).thenReturn(List.of(studentWithoutParent));
        List<StudentDto> studentDto = service.findStudentsWithoutParent();
        Assertions.assertThat(studentDto).isNotNull();
    }

    @Test
    void findStudentsWithoutConnectionWithClass() {
        when(studentRepository.findAllByAcademicClassIsNull()).thenReturn(List.of(studentWithoutClass));
        List<StudentDto> studentDto = service.findStudentsWithoutConnectionWithClass();
        Assertions.assertThat(studentDto).isNotNull();

    }
}