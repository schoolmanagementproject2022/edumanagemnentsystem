package com.epam.edumanagementsystem.student.mapper;

import com.epam.edumanagementsystem.student.model.dto.StudentDto;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.exceptions.ObjectIsNull;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    @Lazy
    private static UserService userService;

    public StudentMapper(UserService userService) {
        this.userService = userService;
    }

    public static Student toStudent(StudentDto studentDto) {
        if (studentDto == null) {
            throw new ObjectIsNull();
        }
        Student student = new Student();
        User user = new User();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setDate(studentDto.getDate());
        student.setAddress(studentDto.getAddress());
        student.setBloodGroup(studentDto.getBloodGroup());
        student.setGender(studentDto.getGender());
        student.setPassword(studentDto.getPassword());
        student.setParent(studentDto.getParent());
        student.setAcademicClass(studentDto.getAcademicClass());
        user.setEmail(studentDto.getEmail());
        user.setRole(studentDto.getRole());
        User save = userService.save(user);
        student.setUser(save);
        return student;
    }

    public static Student toStudent(StudentDto studentDto, UserService userService) {
        if (studentDto == null) {
            throw new ObjectIsNull();
        }
        Student student = new Student();
        User user = new User();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setDate(studentDto.getDate());
        student.setAddress(studentDto.getAddress());
        student.setBloodGroup(studentDto.getBloodGroup());
        student.setGender(studentDto.getGender());
        student.setPassword(studentDto.getPassword());
        student.setParent(studentDto.getParent());
        student.setAcademicClass(studentDto.getAcademicClass());
        user.setEmail(studentDto.getEmail());
        user.setRole(studentDto.getRole());
        User save = userService.save(user);
        student.setUser(save);
        return student;
    }

    public static Student toStudentWithoutSavingUser(StudentDto studentDto) {
        if (studentDto == null) {
            throw new ObjectIsNull();
        }
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setDate(studentDto.getDate());
        student.setAddress(studentDto.getAddress());
        student.setBloodGroup(studentDto.getBloodGroup());
        student.setGender(studentDto.getGender());
        student.setPassword(studentDto.getPassword());
        student.setParent(studentDto.getParent());
        student.setAcademicClass(studentDto.getAcademicClass());
        student.setUser(userService.findByEmail(studentDto.getEmail()));
        return student;
    }

    public static StudentDto toStudentDto(Student student) {
        if (student == null) {
            throw new ObjectIsNull();
        }
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setSurname(student.getSurname());
        studentDto.setEmail(student.getUser().getEmail());
        studentDto.setRole(student.getUser().getRole());
        studentDto.setDate(student.getDate());
        studentDto.setAddress(student.getAddress());
        studentDto.setBloodGroup(student.getBloodGroup());
        studentDto.setGender(student.getGender());
        studentDto.setPassword(student.getPassword());
        studentDto.setParent(student.getParent());
        studentDto.setAcademicClass(student.getAcademicClass());
        return studentDto;
    }

    public static List<Student> toStudentList(List<StudentDto> studentDtos) {
        return studentDtos
                .stream()
                .map(StudentMapper::toStudentWithoutSavingUser)
                .collect(Collectors
                        .toList());
    }

    public static List<StudentDto> toStudentDtoList(List<Student> studentEntities) {
        return studentEntities
                .stream()
                .map(StudentMapper::toStudentDto)
                .collect(Collectors
                        .toList());
    }
}
