package com.epam.edumanagementsystem.admin.journal_agenda.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import java.time.LocalDate;
import java.util.List;

public class ClassworkDto {

    private Long id;
    private String classwork;
    private int grade;
    private LocalDate dateOfClasswork;
    private List<AcademicCourse> coursesOfClasswork;

    public ClassworkDto() {

    }

    public ClassworkDto(Long id, String classwork, int grade, LocalDate dateOfClasswork, List<AcademicCourse> coursesOfClasswork) {
        this.id = id;
        this.classwork = classwork;
        this.grade = grade;
        this.dateOfClasswork = dateOfClasswork;
        this.coursesOfClasswork = coursesOfClasswork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasswork() {
        return classwork;
    }

    public void setClasswork(String classwork) {
        this.classwork = classwork;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDate getDateOfClasswork() {
        return dateOfClasswork;
    }

    public void setDateOfClasswork(LocalDate dateOfClasswork) {
        this.dateOfClasswork = dateOfClasswork;
    }

    public List<AcademicCourse> getCoursesOfClasswork() {
        return coursesOfClasswork;
    }

    public void setCoursesOfClasswork(List<AcademicCourse> coursesOfClasswork) {
        this.coursesOfClasswork = coursesOfClasswork;
    }

    @Override
    public String toString() {
        return "ClassworkDto{" +
                "id=" + id +
                ", classwork='" + classwork + '\'' +
                ", grade=" + grade +
                ", dateOfClasswork=" + dateOfClasswork +
                ", coursesOfClasswork=" + coursesOfClasswork +
                '}';
    }
}
