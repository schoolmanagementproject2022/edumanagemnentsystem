package com.epam.edumanagementsystem.teacher.mapper;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.entity.User;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TeacherMapper {

    public static Teacher toTeacher(TeacherDto teacherDto, User user) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDto.getId());
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setPassword(teacherDto.getPassword());
        teacher.setPicUrl(teacherDto.getPicUrl());
        teacher.setUser(user);
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
        teacherDto.setPicUrl(teacher.getPicUrl());
        return teacherDto;
    }

    public static List<TeacherDto> toListOfTeachersDto(List<Teacher> teachers) {
        List<TeacherDto> listOfTeacherDto = new ArrayList<>();
        for (Teacher teacher : teachers) {
            listOfTeacherDto.add(toDto(teacher));
        }
        return listOfTeacherDto;
    }

    public static List<Teacher> toListOfTeachers(List<TeacherDto> teachersDto, User user) {
        List<Teacher> teachers = new ArrayList<>();
        for (TeacherDto teacherDto : teachersDto) {
            teachers.add(toTeacher(teacherDto, user));
        }
        return teachers;
    }

    public static Set<TeacherDto> toSetOfTeachersDto(Set<Teacher> teachers) {
        Set<TeacherDto> teacherDto = new LinkedHashSet<>();
        for (Teacher teacher : teachers) {
            teacherDto.add(toDto(teacher));
        }
        return teacherDto;
    }
}
