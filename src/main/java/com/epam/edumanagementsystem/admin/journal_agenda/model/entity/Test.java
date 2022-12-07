package com.epam.edumanagementsystem.admin.journal_agenda.model.entity;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "agenda_test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String test;
    int grade;
    LocalDate dateOfTest;
    @ManyToMany
    @JoinTable(name = "course_test_mapping",
            joinColumns = @JoinColumn(name = "agenda_test_id"),
            inverseJoinColumns = @JoinColumn(name = "academic_course_id"))
    private List<AcademicCourse> coursesOfTest;

    public Test() {
    }

    public Test(Long id, String test, int grade, LocalDate dateOfTest, List<AcademicCourse> coursesOfTest) {
        this.id = id;
        this.test = test;
        this.grade = grade;
        this.dateOfTest = dateOfTest;
        this.coursesOfTest = coursesOfTest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public LocalDate getDateOfTest() {
        return dateOfTest;
    }

    public void setDateOfTest(LocalDate dateOfTest) {
        this.dateOfTest = dateOfTest;
    }

    public List<AcademicCourse> getCoursesOfTest() {
        return coursesOfTest;
    }

    public void setCoursesOfTest(List<AcademicCourse> coursesOfTest) {
        this.coursesOfTest = coursesOfTest;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", test='" + test + '\'' +
                ", grade=" + grade +
                ", dateOfTest=" + dateOfTest +
                ", coursesOfTest=" + coursesOfTest +
                '}';
    }
}
