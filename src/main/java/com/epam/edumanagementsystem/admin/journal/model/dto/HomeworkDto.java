package com.epam.edumanagementsystem.admin.journal.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.time.LocalDate;

public class HomeworkDto {

    private Long id;
    private String homework;
    private LocalDate dateOfHomework;
    private AcademicCourse academicCourse;
    private AcademicClass academicClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public LocalDate getDateOfHomework() {
        return dateOfHomework;
    }

    public void setDateOfHomework(LocalDate dateOfHomework) {
        this.dateOfHomework = dateOfHomework;
    }

    public AcademicCourse getCourseOfHomework() {
        return academicCourse;
    }

    public void setCourseOfHomework(AcademicCourse academicCourse) {
        this.academicCourse = academicCourse;
    }

    public AcademicClass getAcademicClass() {
        return academicClass;
    }

    public void setAcademicClass(AcademicClass academicClass) {
        this.academicClass = academicClass;
    }

    @Override
    public String toString() {
        return "HomeworkDto{" +
                "id=" + id +
                ", homework='" + homework + '\'' +
                ", dateOfHomework=" + dateOfHomework +
                ", academicCourse=" + academicCourse +
                ", academicClass=" + academicClass +
                '}';
    }
}
