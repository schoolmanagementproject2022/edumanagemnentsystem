package com.epam.edumanagementsystem.admin.journal_agenda.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.time.LocalDate;
import java.util.List;

public class TestDto {

    private Long id;
    private String test;
    private int grade;
    private LocalDate dateOfTest;
    private List<AcademicCourse> coursesOfTest;

    public TestDto() {
    }

    public TestDto(Long id, String test, int grade, LocalDate dateOfTest, List<AcademicCourse> coursesOfTest) {
        this.id = id;
        this.test = test;
        this.grade = grade;
        this.dateOfTest = dateOfTest;
        this.coursesOfTest = coursesOfTest;
    }

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDate getDateOfTest() {
        return dateOfTest;
    }

    public void setDateOfTest(LocalDate dateOfTest) {
        this.dateOfTest = dateOfTest;
    }

    public List<AcademicCourse> getCoursesOfTest() {
        return coursesOfTest;
    }

    public void setCoursesOfTest(List<AcademicCourse> coursesOfTest) {
        this.coursesOfTest = coursesOfTest;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "id=" + id +
                ", test='" + test + '\'' +
                ", grade=" + grade +
                ", dateOfTest=" + dateOfTest +
                ", coursesOfTest=" + coursesOfTest +
                '}';
    }
}
