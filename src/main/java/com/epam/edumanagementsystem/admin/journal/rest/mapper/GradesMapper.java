package com.epam.edumanagementsystem.admin.journal.rest.mapper;

import com.epam.edumanagementsystem.admin.journal.model.dto.GradesDto;
import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;

public class GradesMapper {

    public static Grades toGrades(GradesDto gradesDto) {
        Grades grades = new Grades();
        grades.setId(grades.getId());
        grades.setGrade(grades.getGrade());
        grades.setStudent(grades.getStudent());
        grades.setClassworkList(grades.getClassworkList());
        grades.setTestList(grades.getTestList());
        grades.setHomeworkList(grades.getHomeworkList());

        return grades;
    }

    public static GradesDto toDto(Grades grades) {
        GradesDto gradesDto = new GradesDto();
        gradesDto.setId(gradesDto.getId());
        gradesDto.setGrade(gradesDto.getGrade());
        gradesDto.setStudent(gradesDto.getStudent());
        gradesDto.setClassworkList(gradesDto.getClassworkList());
        gradesDto.setTestList(gradesDto.getTestList());
        gradesDto.setHomeworkList(gradesDto.getHomeworkList());
        return gradesDto;
    }
}
