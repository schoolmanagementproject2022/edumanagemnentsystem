package com.epam.edumanagementsystem.admin.journal.model.entity;

import com.epam.edumanagementsystem.student.model.entity.Student;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int grade;
    @ManyToOne
    private Student student;
    @ManyToOne

    private Homework homework;
    @ManyToOne

    private Classwork classwork;
    @ManyToOne

    private Test test;

    public Grade(Long id, int grade, Student student, Homework homework, Classwork classwork, Test test) {
        this.id = id;
        this.grade = grade;
        this.student = student;
        this.homework = homework;
        this.classwork = classwork;
        this.test = test;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return grade == grade1.grade && Objects.equals(id, grade1.id) && Objects.equals(student, grade1.student) && Objects.equals(homework, grade1.homework) && Objects.equals(classwork, grade1.classwork) && Objects.equals(test, grade1.test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grade, student, homework, classwork, test);
    }
}
