package com.epam.edumanagementsystem.admin.journal_agenda.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.time.LocalDate;
import java.util.List;

public class HomeworkDto {
    private Long id;
    private String homework;
    private int grade;
    private LocalDate dateOfHomework;
    private List<AcademicCourse> coursesOfHomework;

    public HomeworkDto() {

    }

    public HomeworkDto(Long id, String homework, int grade, LocalDate dateOfHomework, List<AcademicCourse> coursesOfHomework) {
        this.id = id;
        this.homework = homework;
        this.grade = grade;
        this.dateOfHomework = dateOfHomework;
        this.coursesOfHomework = coursesOfHomework;
    }

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDate getDateOfHomework() {
        return dateOfHomework;
    }

    public void setDateOfHomework(LocalDate dateOfHomework) {
        this.dateOfHomework = dateOfHomework;
    }

    public List<AcademicCourse> getCoursesOfHomework() {
        return coursesOfHomework;
    }

    public void setCoursesOfHomework(List<AcademicCourse> coursesOfHomework) {
        this.coursesOfHomework = coursesOfHomework;
    }

    @Override
    public String toString() {
        return "HomeworkDto{" +
                "id=" + id +
                ", homework='" + homework + '\'' +
                ", grade=" + grade +
                ", dateOfHomework=" + dateOfHomework +
                ", coursesOfHomework=" + coursesOfHomework +
                '}';
    }
}
