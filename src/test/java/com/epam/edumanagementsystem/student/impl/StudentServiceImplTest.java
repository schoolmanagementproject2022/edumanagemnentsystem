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
import com.epam.edumanagementsystem.util.exception.UserNotFoundException;
import com.epam.edumanagementsystem.util.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void testFindAllWhenListIsEmptyNegativeCase() {
        when(studentRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(studentService.findAll().isEmpty());
        verify(studentRepository).findAll();
    }

    @Test
    void testFindAllStudents() {
        //when
        studentService.findAll();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void testFindAllPositiveCaseWhenAddedOneStudent() {
        User teacherUser = new User();
        teacherUser.setEmail("gariktepanosyan@gmail.com");
        teacherUser.setId(1L);
        teacherUser.setRole("TEACHER");

        Teacher teacher = new Teacher();
        teacher.setAcademicClass(new LinkedHashSet<>());
        teacher.setAcademicCourseSet(new LinkedHashSet<>());
        teacher.setId(1L);
        teacher.setName("Garik");
        teacher.setPassword("password");
        teacher.setSubjectSet(new LinkedHashSet<>());
        teacher.setSurname("Tepanosyan");
        teacher.setUser(teacherUser);

        AcademicClass academicClass = new AcademicClass();
        academicClass.setAcademicCourseSet(new LinkedHashSet<>());
        academicClass.setClassNumber("1A");
        academicClass.setClassroomTeacher(teacher);
        academicClass.setCoursesForTimetableList(new ArrayList<>());
        academicClass.setId(1L);
        academicClass.setStudent(new LinkedHashSet<>());
        academicClass.setTeacher(new LinkedHashSet<>());

        User parentUser = new User();
        parentUser.setEmail("gariktepanosyan@example.org");
        parentUser.setId(2L);
        parentUser.setRole("PARENT");

        Parent parent = new Parent();
        parent.setId(2L);
        parent.setName("Name");
        parent.setPassword("password");
        parent.setSurname("Surname");
        parent.setUser(parentUser);

        User studentUser = new User();
        studentUser.setEmail("garik1text@example.org");
        studentUser.setId(3L);
        studentUser.setRole("STUDENT");

        Student student = new Student();
        student.setAcademicClass(academicClass);
        student.setAddress("1 Paruyr Sevak St");
        student.setBloodGroup(BloodGroup.A_PLUS);
        student.setDate(LocalDate.ofEpochDay(1L));
        student.setGender(Gender.MALE);
        student.setId(3L);
        student.setName("Name");
        student.setParent(parent);
        student.setPassword("password");
        student.setSurname("Surname");
        student.setUser(studentUser);

        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(student);
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(1, studentService.findAll().size());
        verify(studentRepository).findAll();
    }

    @Test
    void testFindAllPositiveCaseCheckListSizeWhenCreatedTwoStudents() {
        User teacherUser = new User();
        teacherUser.setEmail("gariktepanosyan1@example.org");
        teacherUser.setId(1L);
        teacherUser.setRole("TEACHER");

        Teacher teacher = new Teacher();
        teacher.setAcademicClass(new LinkedHashSet<>());
        teacher.setAcademicCourseSet(new LinkedHashSet<>());
        teacher.setId(1L);
        teacher.setName("Name");
        teacher.setPassword("password");
        teacher.setSubjectSet(new LinkedHashSet<>());
        teacher.setSurname("Surname");
        teacher.setUser(teacherUser);

        AcademicClass academicClass = new AcademicClass();
        academicClass.setAcademicCourseSet(new LinkedHashSet<>());
        academicClass.setClassNumber("2A");
        academicClass.setClassroomTeacher(teacher);
        academicClass.setCoursesForTimetableList(new ArrayList<>());
        academicClass.setId(1L);
        academicClass.setStudent(new HashSet<>());
        academicClass.setTeacher(new HashSet<>());

        User parentUser = new User();
        parentUser.setEmail("garik2t@example.org");
        parentUser.setId(2L);
        parentUser.setRole("PARENT");

        Parent parent = new Parent();
        parent.setId(2L);
        parent.setName("Name");
        parent.setPassword("password");
        parent.setSurname("Surname");
        parent.setUser(parentUser);

        User studentUser = new User();
        studentUser.setEmail("garik3t@example.org");
        studentUser.setId(3L);
        studentUser.setRole("STUDENT");

        Student student = new Student();
        student.setAcademicClass(academicClass);
        student.setAddress("P.S. St");
        student.setBloodGroup(BloodGroup.A_PLUS);
        student.setDate(LocalDate.ofEpochDay(1L));
        student.setGender(Gender.MALE);
        student.setId(3L);
        student.setName("Name");
        student.setParent(parent);
        student.setPassword("password");
        student.setSurname("Surname");
        student.setUser(studentUser);

        User secondTeacherUser = new User();
        secondTeacherUser.setEmail("garik4t@example.org");
        secondTeacherUser.setId(4L);
        secondTeacherUser.setRole("TEACHER");

        Teacher secondTeacher = new Teacher();
        secondTeacher.setAcademicClass(new LinkedHashSet<>());
        secondTeacher.setAcademicCourseSet(new LinkedHashSet<>());
        secondTeacher.setId(4L);
        secondTeacher.setName("Teacher");
        secondTeacher.setPassword("password");
        secondTeacher.setSubjectSet(new LinkedHashSet<>());
        secondTeacher.setSurname("Surname");
        secondTeacher.setUser(secondTeacherUser);

        AcademicClass secondAcademicClass = new AcademicClass();
        secondAcademicClass.setAcademicCourseSet(new LinkedHashSet<>());
        secondAcademicClass.setClassNumber("3A");
        secondAcademicClass.setClassroomTeacher(secondTeacher);
        secondAcademicClass.setCoursesForTimetableList(new ArrayList<>());
        secondAcademicClass.setId(4L);
        secondAcademicClass.setStudent(new LinkedHashSet<>());
        secondAcademicClass.setTeacher(new LinkedHashSet<>());

        User secondParentUser = new User();
        secondParentUser.setEmail("Garik.exam@example.org");
        secondParentUser.setId(5L);
        secondParentUser.setRole("PARENT");

        Parent parent1 = new Parent();
        parent1.setId(5L);
        parent1.setName("Parent");
        parent1.setPassword("password");
        parent1.setSurname("Surname");
        parent1.setUser(secondParentUser);

        User secondStudentUser = new User();
        secondStudentUser.setEmail("garik.example@example.org");
        secondStudentUser.setId(6L);
        secondStudentUser.setRole("STUDENT");

        Student secondStudent = new Student();
        secondStudent.setAcademicClass(secondAcademicClass);
        secondStudent.setAddress("42 St");
        secondStudent.setBloodGroup(BloodGroup.A_PLUS);
        secondStudent.setDate(LocalDate.ofEpochDay(1L));
        secondStudent.setGender(Gender.MALE);
        secondStudent.setId(6L);
        secondStudent.setName("Student");
        secondStudent.setParent(parent1);
        secondStudent.setPassword("password");
        secondStudent.setSurname("Surname");
        secondStudent.setUser(secondStudentUser);

        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(secondStudent);
        studentList.add(student);

        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(2, studentService.findAll().size());
        verify(studentRepository).findAll();
    }

    @Test
    void testCreateNegativeCaseWhenParamsIsNull() {
        assertThrows(UserNotFoundException.class, () -> studentService.create(StudentMapper.toStudentDto(null), userService));
    }

    @Test
    void testCreateWithGetterAndSetterPositiveCase() {

        User teacherUser = new User();
        teacherUser.setEmail("garik.teacher@example.org");
        teacherUser.setId(1L);
        teacherUser.setRole("TEACHER");

        Teacher teacher = new Teacher();
        teacher.setAcademicClass(new LinkedHashSet<>());
        teacher.setAcademicCourseSet(new LinkedHashSet<>());
        teacher.setId(1L);
        teacher.setName("Name");
        teacher.setPassword("password");
        teacher.setSubjectSet(new LinkedHashSet<>());
        teacher.setSurname("Surname");
        teacher.setUser(teacherUser);

        AcademicClass academicClass = new AcademicClass();
        academicClass.setAcademicCourseSet(new HashSet<>());
        academicClass.setClassNumber("2C");
        academicClass.setClassroomTeacher(teacher);
        academicClass.setCoursesForTimetableList(new ArrayList<>());
        academicClass.setId(1L);
        academicClass.setStudent(new LinkedHashSet<>());
        academicClass.setTeacher(new LinkedHashSet<>());

        User parentUSer = new User();
        parentUSer.setEmail("garik.parent@example.org");
        parentUSer.setId(2L);
        parentUSer.setRole("PARENT");

        Parent parent = new Parent();
        parent.setId(2L);
        parent.setName("Name");
        parent.setPassword("password");
        parent.setSurname("Surname");
        parent.setUser(parentUSer);

        StudentDto studentDto = mock(StudentDto.class);
        when(studentDto.getAcademicClass()).thenReturn(academicClass);
        when(studentDto.getParent()).thenReturn(parent);
        when(studentDto.getBloodGroup()).thenReturn(BloodGroup.A_PLUS);
        when(studentDto.getGender()).thenReturn(Gender.MALE);
        when(studentDto.getId()).thenReturn(123L);
        when(studentDto.getAddress()).thenReturn("1 St");
        when(studentDto.getEmail()).thenReturn("garik.studentDto@example.org");
        when(studentDto.getName()).thenReturn("Name");
        when(studentDto.getPassword()).thenReturn("password");
        when(studentDto.getRole()).thenReturn("STUDENT");
        when(studentDto.getSurname()).thenReturn("Surname");
        when(studentDto.getDate()).thenReturn(LocalDate.ofEpochDay(1L));
        studentService.create(studentDto, userService);
    }

    @Test
    void testUpdate() {
        User teacherUser = new User();
        teacherUser.setEmail("garik.teacher@example.org");
        teacherUser.setId(1L);
        teacherUser.setRole("TEACHER");

        Teacher teacher = new Teacher();
        teacher.setAcademicClass(new LinkedHashSet<>());
        teacher.setAcademicCourseSet(new LinkedHashSet<>());
        teacher.setId(1L);
        teacher.setName("Name");
        teacher.setPassword("password");
        teacher.setSubjectSet(new LinkedHashSet<>());
        teacher.setSurname("Surname");
        teacher.setUser(teacherUser);

        AcademicClass academicClass = new AcademicClass();
        academicClass.setAcademicCourseSet(new LinkedHashSet<>());
        academicClass.setClassNumber("3B");
        academicClass.setClassroomTeacher(teacher);
        academicClass.setCoursesForTimetableList(new ArrayList<>());
        academicClass.setId(1L);
        academicClass.setStudent(new LinkedHashSet<>());
        academicClass.setTeacher(new LinkedHashSet<>());

        User parentUser = new User();
        parentUser.setEmail("garik.parent@google.org");
        parentUser.setId(2L);
        parentUser.setRole("PARENT");

        Parent parent = new Parent();
        parent.setId(2L);
        parent.setName("Name");
        parent.setPassword("password");
        parent.setSurname("Surname");
        parent.setUser(parentUser);

        User studentUser = new User();
        studentUser.setEmail("garik.student@google.org");
        studentUser.setId(3L);
        studentUser.setRole("STUDENT");

        Student student = new Student();
        student.setAcademicClass(academicClass);
        student.setAddress("1 St");
        student.setBloodGroup(BloodGroup.A_PLUS);
        student.setDate(LocalDate.ofEpochDay(1L));
        student.setGender(Gender.MALE);
        student.setId(3L);
        student.setName("Name");
        student.setParent(parent);
        student.setPassword("password");
        student.setSurname("Surname");
        student.setUser(studentUser);
        when(studentRepository.save(any())).thenReturn(student);

        User secondTeacherUser = new User();
        secondTeacherUser.setEmail("garik.secondteacher@google.org");
        secondTeacherUser.setId(4L);
        secondTeacherUser.setRole("TEACHER");

        Teacher secondTeacher = new Teacher();
        secondTeacher.setAcademicClass(new LinkedHashSet<>());
        secondTeacher.setAcademicCourseSet(new LinkedHashSet<>());
        secondTeacher.setId(4L);
        secondTeacher.setName("Name");
        secondTeacher.setPassword("password");
        secondTeacher.setSubjectSet(new LinkedHashSet<>());
        secondTeacher.setSurname("Surname");
        secondTeacher.setUser(secondTeacherUser);

        AcademicClass secondAcademicClass = new AcademicClass();
        secondAcademicClass.setAcademicCourseSet(new LinkedHashSet<>());
        secondAcademicClass.setClassNumber("10B");
        secondAcademicClass.setClassroomTeacher(secondTeacher);
        secondAcademicClass.setCoursesForTimetableList(new ArrayList<>());
        secondAcademicClass.setId(4L);
        secondAcademicClass.setStudent(new LinkedHashSet<>());
        secondAcademicClass.setTeacher(new LinkedHashSet<>());

        User secondParentUser = new User();
        secondParentUser.setEmail("garik.secondparent@google.org");
        secondParentUser.setId(5L);
        secondParentUser.setRole("PARENT");

        Parent secondParent = new Parent();
        secondParent.setId(5L);
        secondParent.setName("Name");
        secondParent.setPassword("password");
        secondParent.setSurname("Surname");
        secondParent.setUser(secondParentUser);

        User secondStudentUser = new User();
        secondStudentUser.setEmail("garik.secondStudent@google.org");
        secondStudentUser.setId(6L);
        secondStudentUser.setRole("STUDENT");

        Student secondStudent = new Student();
        secondStudent.setAcademicClass(secondAcademicClass);
        secondStudent.setAddress("2 St");
        secondStudent.setBloodGroup(BloodGroup.A_PLUS);
        secondStudent.setDate(LocalDate.ofEpochDay(1L));
        secondStudent.setGender(Gender.MALE);
        secondStudent.setId(6L);
        secondStudent.setName("Name");
        secondStudent.setParent(secondParent);
        secondStudent.setPassword("password");
        secondStudent.setSurname("Surname");
        secondStudent.setUser(secondStudentUser);
        assertSame(student, studentService.update(secondStudent));
        verify(studentRepository).save(any());
    }

    @Test
    void testUpdateNegativeCase() {
        assertThrows(UserNotFoundException.class, () -> studentService.update(null));
    }

    @Test
    void testFindByUserId() {
        User teacherUser = new User();
        teacherUser.setEmail("garik.e@example.org");
        teacherUser.setId(1L);
        teacherUser.setRole("TEACHER");

        Teacher teacher = new Teacher();
        teacher.setAcademicClass(new LinkedHashSet<>());
        teacher.setAcademicCourseSet(new LinkedHashSet<>());
        teacher.setId(1L);
        teacher.setName("Name");
        teacher.setPassword("password");
        teacher.setSubjectSet(new LinkedHashSet<>());
        teacher.setSurname("Surname");
        teacher.setUser(teacherUser);

        AcademicClass academicClass = new AcademicClass();
        academicClass.setAcademicCourseSet(new HashSet<>());
        academicClass.setClassNumber("8B");
        academicClass.setClassroomTeacher(teacher);
        academicClass.setCoursesForTimetableList(new ArrayList<>());
        academicClass.setId(1L);
        academicClass.setStudent(new LinkedHashSet<>());
        academicClass.setTeacher(new LinkedHashSet<>());

        User parentUser = new User();
        parentUser.setEmail("garik.parent@example.org");
        parentUser.setId(2L);
        parentUser.setRole("PARENT");

        Parent parent = new Parent();
        parent.setId(2L);
        parent.setName("Name");
        parent.setPassword("password");
        parent.setSurname("Surname");
        parent.setUser(parentUser);

        User studentUser = new User();
        studentUser.setEmail("garik.student@example.org");
        studentUser.setId(3L);
        studentUser.setRole("STUDENT");

        Student student = new Student();
        student.setAcademicClass(academicClass);
        student.setAddress("4 St");
        student.setBloodGroup(BloodGroup.A_PLUS);
        student.setDate(LocalDate.ofEpochDay(1L));
        student.setGender(Gender.MALE);
        student.setId(3L);
        student.setName("Name");
        student.setParent(parent);
        student.setPassword("password");
        student.setSurname("Surname");
        student.setUser(studentUser);
        when(studentRepository.findByUserId(any())).thenReturn(student);
        assertSame(student, studentService.findByUserId(3L));
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
}

