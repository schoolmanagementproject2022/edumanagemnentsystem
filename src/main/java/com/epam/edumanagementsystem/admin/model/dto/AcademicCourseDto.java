package com.epam.edumanagementsystem.admin.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.Subject;

public class AcademicCourseDto {
    private Long id;
    private String academicCourseName;
    private Subject subject;

    public AcademicCourseDto() {

    }

    public AcademicCourseDto(Long id, String academicCourseName, Subject subject) {
        this.id = id;
        this.academicCourseName = academicCourseName;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return academicCourseName;
    }

    public void setName(String name) {
        this.academicCourseName = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
