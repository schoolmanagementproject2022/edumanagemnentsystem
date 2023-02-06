package com.epam.edumanagementsystem.admin.journal.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.student.model.entity.Student;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "grades")
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int gradeHomework;
    private int gradeTest;
    private int gradeClasswork;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    private LocalDate date;
    @OneToOne
    @JoinTable(name = "classwork_classworkGrade_mapping",
            joinColumns = @JoinColumn(name = "grades_id"),
            inverseJoinColumns = @JoinColumn(name = "classwork_id"))
    private Classwork classwork;
    @OneToOne
    @JoinTable(name = "test_testGrade_mapping",
            joinColumns = @JoinColumn(name = "grades_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private Test test;
    @OneToOne
    @JoinTable(name = "homework_homeworkGrade_mapping",
            joinColumns = @JoinColumn(name = "grades_id"),
            inverseJoinColumns = @JoinColumn(name = "homework_id"))
    private Homework homework;
    @ManyToOne
    private AcademicCourse academicCourse;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classwork getClasswork() {
        return classwork;
    }

    public void setClasswork(Classwork classwork) {
        this.classwork = classwork;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public int getGradeHomework() {
        return gradeHomework;
    }

    public void setGradeHomework(int gradeHomework) {
        this.gradeHomework = gradeHomework;
    }

    public int getGradeTest() {
        return gradeTest;
    }

    public void setGradeTest(int gradeTest) {
        this.gradeTest = gradeTest;
    }

    public int getGradeClasswork() {
        return gradeClasswork;
    }

    public void setGradeClasswork(int gradeClasswork) {
        this.gradeClasswork = gradeClasswork;
    }

    public AcademicCourse getAcademicCourse() {
        return academicCourse;
    }

    public void setAcademicCourse(AcademicCourse academicCourse) {
        this.academicCourse = academicCourse;
    }

    @Override
    public String toString() {
        return "Grades{" +
                "id=" + id +
                ", gradeHomework=" + gradeHomework +
                ", gradeTest=" + gradeTest +
                ", gradeClasswork=" + gradeClasswork +
                ", student=" + student +
                ", classwork=" + classwork +
                ", test=" + test +
                ", homework=" + homework +
                '}';
    }
}
