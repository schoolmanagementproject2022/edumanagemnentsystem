package com.epam.edumanagementsystem.admin.journal.model.dto;

import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.time.LocalDate;
import java.util.List;

public class HomeworkDto {
    private Long id;
    private String homework;
    private List<Grades> grade;
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

    public List<Grades> getGrade() {
        return grade;
    }

    public void setGrade(List<Grades> grade) {
        this.grade = grade;
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
                ", grade=" + grade +
                ", dateOfHomework=" + dateOfHomework +
                ", academicCourse=" + academicCourse +
                ", academicClass=" + academicClass +
                '}';
    }
}
