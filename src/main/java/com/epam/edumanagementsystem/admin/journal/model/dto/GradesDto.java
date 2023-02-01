package com.epam.edumanagementsystem.admin.journal.model.dto;

import com.epam.edumanagementsystem.admin.journal.model.entity.Classwork;
import com.epam.edumanagementsystem.admin.journal.model.entity.Homework;
import com.epam.edumanagementsystem.admin.journal.model.entity.Test;
import com.epam.edumanagementsystem.student.model.entity.Student;

import java.util.List;

public class GradesDto {
    private Long id;
    private int gradeClasswork;
    private int gradeHomework;
    private int gradeTest;
    private Student student;
    private List<Classwork> classworkList;
    private List<Test> testList;
    private List<Homework> homeworkList;

    public GradesDto() {
    }

    public GradesDto(Long id, int grade, Student student, List<Classwork> classworkList, List<Test> testList, List<Homework> homeworkList) {
        this.id = id;
        this.gradeClasswork = grade;
        this.student = student;
        this.classworkList = classworkList;
        this.testList = testList;
        this.homeworkList = homeworkList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGradeClasswork() {
        return gradeClasswork;
    }

    public void setGradeClasswork(int gradeClasswork) {
        this.gradeClasswork = gradeClasswork;
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

    public void setClassworkList(List<Classwork> classworkList) {
        this.classworkList = classworkList;
    }

    public List<Test> getTestList() {
        return testList;
    }

    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }

    public List<Homework> getHomeworkList() {
        return homeworkList;
    }

    public void setHomeworkList(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }

    @Override
    public String toString() {
        return "GradesDto{" +
                "id=" + id +
                ", grade=" + gradeClasswork +
                ", student=" + student +
                ", classworkList=" + classworkList +
                ", testList=" + testList +
                ", homeworkList=" + homeworkList +
                '}';
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
}
