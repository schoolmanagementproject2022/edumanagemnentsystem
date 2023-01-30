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
    private int grade;
    @ManyToOne
    private Student student;
    @ManyToMany
    @JoinTable(name = "classwork_grades_mapping",
            joinColumns = @JoinColumn(name = "grades_id"),
            inverseJoinColumns = @JoinColumn(name = "classwork_id"))
    private List<Classwork> classworkList;
    @ManyToMany
    @JoinTable(name = "test_grades_mapping",
            joinColumns = @JoinColumn(name = "grades_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<Test> testList;
    @ManyToMany
    @JoinTable(name = "homework_grades_mapping",
            joinColumns = @JoinColumn(name = "grades_id"),
            inverseJoinColumns = @JoinColumn(name = "homework_id"))
    private List<Homework> homeworkList;

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
        return "Grades{" +
                "id=" + id +
                ", grade=" + grade +
                ", student=" + student +
                ", classwork=" + classworkList +
                ", test=" + testList +
                ", homework=" + homeworkList +
                '}';
    }
}
