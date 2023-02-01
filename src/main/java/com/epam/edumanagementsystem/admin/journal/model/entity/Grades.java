package com.epam.edumanagementsystem.admin.journal.model.entity;

import com.epam.edumanagementsystem.student.model.entity.Student;

import javax.persistence.*;
import java.util.List;

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
    @OneToOne
    private Classwork classwork;
    @OneToOne

    private Test test;
    @OneToOne

    private Homework homework;

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

    public List<Classwork> getClassworkList() {
        return classworkList;
    }

    public void setClassworkList(Classwork classworkList) {
        this.classwor = classwork;
    }

    public Test getTestList() {
        return test;
    }

    public void setTestList(Test test) {
        this.test = test;
    }

    public Homework getHomeworkList() {
        return homework;
    }

    public void setHomeworkList(Homework homework) {
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

    @Override
    public String toString() {
        return "Grades{" +
                "id=" + id +
                ", gradeHomework=" + gradeHomework +
                ", gradeTest=" + gradeTest +
                ", gradeClasswork=" + gradeClasswork +
                ", student=" + student +
                ", classworkList=" + classworkList +
                ", testList=" + testList +
                ", homeworkList=" + homeworkList +
                '}';
    }
}
