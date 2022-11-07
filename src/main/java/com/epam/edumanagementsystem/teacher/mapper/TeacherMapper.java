package com.epam.edumanagementsystem.teacher.mapper;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class TeacherMapper {

    private static UserService userService;

    public TeacherMapper(UserService userService) {
        TeacherMapper.userService = userService;
    }

    public static Teacher toTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDto.getId());
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setPassword(teacherDto.getPassword());
        return teacher;
    }

    public static Teacher toTeacher(TeacherDto teacherDto, UserService userService) {
        Teacher teacher = new Teacher();
        User user = new User();
        teacher.setId(teacherDto.getId());
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setPassword(teacherDto.getPassword());
        user.setEmail(teacherDto.getEmail());
        user.setRole(teacherDto.getRole());
        User save = userService.save(user);
        teacher.setUser(save);
        return teacher;
    }

    public static Teacher toTeacherWithoutSavingUser(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDto.getId());
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setUser(userService.findByEmail(teacherDto.getEmail()));
        teacher.setPassword(teacherDto.getPassword());
        return teacher;
    }

    public static TeacherDto toDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setName(teacher.getName());
        teacherDto.setSurname(teacher.getSurname());
        teacherDto.setEmail(teacher.getUser().getEmail());
        teacherDto.setRole(teacher.getUser().getRole());
        teacherDto.setPassword(teacher.getPassword());
        return teacherDto;
    }

    public static List<TeacherDto> toListOfTeachersDto(List<Teacher> teachers) {
        List<TeacherDto> teacherDto = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherDto.add(toDto(teacher));
        }
        return teacherDto;
    }

    public static List<Teacher> toListOfTeachers(List<TeacherDto> teachersDto) {
        List<Teacher> teachers = new ArrayList<>();
        for (TeacherDto teacherDto : teachersDto) {
            teachers.add(toTeacherWithoutSavingUser(teacherDto));
        }
        return teachers;
    }
}
