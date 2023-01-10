package com.epam.edumanagementsystem.teacher.mapper;

import com.epam.edumanagementsystem.teacher.model.dto.TeacherDto;
import com.epam.edumanagementsystem.teacher.model.dto.TeacherEditDto;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TeacherMapper {

    private TeacherMapper() {
    }

    public static Teacher mapToTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDto.getId());
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setPassword(teacherDto.getPassword());
        teacher.setPicUrl(teacherDto.getPicUrl());
        return teacher;
    }

    public static TeacherDto mapToTeacherDto(Teacher teacher) {
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

    public static TeacherEditDto mapToTeacherEditDto(Teacher teacher) {
        TeacherEditDto teacherEditDto = new TeacherEditDto();
        teacherEditDto.setId(teacher.getId());
        teacherEditDto.setName(teacher.getName());
        teacherEditDto.setSurname(teacher.getSurname());
        teacherEditDto.setEmail(teacher.getUser().getEmail());
        teacherEditDto.setPicUrl(teacher.getPicUrl());
        return teacherEditDto;
    }

    public static List<TeacherDto> mapToTeacherDtoList(List<Teacher> teacherList) {
        return teacherList.stream()
                .map(TeacherMapper::mapToTeacherDto)
                .collect(Collectors.toList());
    }

    public static Set<TeacherDto> mapToTeacherDtoSet(Set<Teacher> teacherSet) {
        return teacherSet.stream()
                .map(TeacherMapper::mapToTeacherDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
