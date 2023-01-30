package com.epam.edumanagementsystem.admin.journal.model.dto;

import com.epam.edumanagementsystem.admin.journal.model.entity.Grades;
import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.time.LocalDate;
import java.util.List;

public class TestDto {

    private Long id;
    private String test;
    private List<Grades> grade;
    private LocalDate dateOfTest;
    private AcademicCourse academicCourse;
    private AcademicClass academicClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public List<Grades> getGrade() {
        return grade;
    }

    public void setGrade(List<Grades> grade) {
        this.grade = grade;
    }

    public LocalDate getDateOfTest() {
        return dateOfTest;
    }

    public void setDateOfTest(LocalDate dateOfTest) {
        this.dateOfTest = dateOfTest;
    }

    public AcademicCourse getCourseOfTest() {
        return academicCourse;
    }

    public void setCourseOfTest(AcademicCourse academicCourse) {
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
        return "TestDto{" + "id=" + id + ", test='" + test + '\'' + ", grade=" + grade + ", dateOfTest=" + dateOfTest + ", academicCourse=" + academicCourse + ", academicClass=" + academicClass + '}';
    }
}
