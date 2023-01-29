package com.epam.edumanagementsystem.admin.journal.model.dto;

import com.epam.edumanagementsystem.admin.journal.model.entity.Classwork;
import com.epam.edumanagementsystem.admin.journal.model.entity.Homework;
import com.epam.edumanagementsystem.admin.journal.model.entity.Test;
import com.epam.edumanagementsystem.student.model.entity.Student;

public class GradeDto {
    private Long id;
    private Student student;
    private Classwork classwork;
    private Homework homework;
    private Test test;

    public GradeDto(Long id, Student student, Classwork classwork, Homework homework, Test test) {
        this.id = id;
        this.student = student;
        this.classwork = classwork;
        this.homework = homework;
        this.test = test;
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

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "GradeDto{" +
                "id=" + id +
                ", student=" + student +
                ", classwork=" + classwork +
                ", homework=" + homework +
                ", test=" + test +
                '}';
    }
}
