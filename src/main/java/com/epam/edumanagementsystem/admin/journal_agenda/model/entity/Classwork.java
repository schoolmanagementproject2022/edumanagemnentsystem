package com.epam.edumanagementsystem.admin.journal_agenda.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "agenda_classwork")
public class Classwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String classwork;
    private int grade;
    private LocalDate dateOfClasswork;
//    @ManyToMany
//    @JoinTable(name = "course_classwork_mapping",
//            joinColumns = @JoinColumn(name = "agenda_classwork_id"),
//            inverseJoinColumns = @JoinColumn(name = "academic_course_id"))
//    private List<AcademicCourse> coursesOfClasswork;

    public Classwork() {
    }

    public Classwork(Long id, String classwork, int grade, LocalDate dateOfClasswork, List<AcademicCourse> coursesOfClasswork) {
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
        return "Classwork{" +
                "id=" + id +
                ", classwork='" + classwork + '\'' +
                ", grade=" + grade +
                ", dateOfClasswork=" + dateOfClasswork +
                ", coursesOfClasswork=" + coursesOfClasswork +
                '}';
    }
}
