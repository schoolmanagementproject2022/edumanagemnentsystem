package com.epam.edumanagementsystem.teacher.mapper;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherMapper {
    public static Teacher toTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDto.getId());
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setPassword(teacherDto.getPassword());
        return teacher;
    }

    public static TeacherDto toDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setName(teacher.getName());
        teacherDto.setSurname(teacher.getSurname());
        teacherDto.setEmail(teacher.getEmail());
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
            teachers.add(toTeacher(teacherDto));
        }
        return teachers;
    }
}
