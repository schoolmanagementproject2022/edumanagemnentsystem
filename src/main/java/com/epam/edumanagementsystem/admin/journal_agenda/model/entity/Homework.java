package com.epam.edumanagementsystem.admin.journal_agenda.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "agenda_homework")
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String homework;
    int grade;
    LocalDate dateOfHomework;
    @ManyToMany
    @JoinTable(name = "course_homework_mapping",
            joinColumns = @JoinColumn(name = "agenda_homework_id"),
            inverseJoinColumns = @JoinColumn(name = "academic_course_id"))
    List<AcademicCourse> coursesOfHomework;

    public Homework() {
    }

    public Homework(Long id, String homework, int grade, LocalDate dateOfHomework, List<AcademicCourse> coursesOfHomework) {
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
        return "Homework{" +
                "id=" + id +
                ", homework='" + homework + '\'' +
                ", grade=" + grade +
                ", dateOfHomework=" + dateOfHomework +
                ", coursesOfHomework=" + coursesOfHomework +
                '}';
    }
}
