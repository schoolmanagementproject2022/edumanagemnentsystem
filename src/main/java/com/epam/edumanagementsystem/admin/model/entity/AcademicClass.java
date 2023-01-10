package com.epam.edumanagementsystem.admin.model.entity;


import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "academicClass")
public class AcademicClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String classNumber;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "academicClass_teacher_mapping",
            joinColumns = @JoinColumn(name = "academicClass_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teachers;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "academicClass_academicCourse_mapping",
            joinColumns = @JoinColumn(name = "academicClass_id"),
            inverseJoinColumns = @JoinColumn(name = "academicCourse_id"))
    private Set<AcademicCourse> academicCourseSet;

    @OneToOne
    private Teacher classroomTeacher;

    @ManyToMany
    private List<CoursesForTimetable> coursesForTimetableList = new ArrayList<>();

    @OneToMany
    private Set<Student> student;

    public AcademicClass() {
    }

    public AcademicClass(Long id, String classNumber, Set<Teacher> teachers, Set<AcademicCourse> academicCourseSet, Teacher classroomTeacher, List<CoursesForTimetable> coursesForTimetableList, Set<Student> student) {
        this.id = id;
        this.classNumber = classNumber;
        this.teachers = teachers;
        this.academicCourseSet = academicCourseSet;
        this.classroomTeacher = classroomTeacher;
        this.coursesForTimetableList = coursesForTimetableList;
        this.student = student;
    }

    public AcademicClass(String classNumber) {
        this.classNumber = classNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teacher) {
        this.teachers = teacher;
    }

    public Set<AcademicCourse> getAcademicCourseSet() {
        return academicCourseSet;
    }

    public void setAcademicCourseSet(Set<AcademicCourse> academicCourseSet) {
        this.academicCourseSet = academicCourseSet;
    }

    public Teacher getClassroomTeacher() {
        return classroomTeacher;
    }

    public void setClassroomTeacher(Teacher classroomTeacher) {
        this.classroomTeacher = classroomTeacher;
    }

    public List<CoursesForTimetable> getCoursesForTimetableList() {
        return coursesForTimetableList;
    }

    public void setCoursesForTimetableList(List<CoursesForTimetable> coursesForTimetableList) {
        this.coursesForTimetableList = coursesForTimetableList;
    }

    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClass that = (AcademicClass) o;
        return Objects.equals(id, that.id) && Objects.equals(classNumber, that.classNumber) && Objects.equals(classroomTeacher, that.classroomTeacher) && Objects.equals(teachers, that.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classNumber, classroomTeacher, student, teachers);
    }

    @Override
    public String toString() {
        return "AcademicClass{" +
                "id=" + id +
                ", classNumber='" + classNumber + '\'' +
                ", teachers=" + teachers +
                ", academicCourseSet=" + academicCourseSet +
                ", classroomTeacher=" + classroomTeacher +
                ", coursesForTimetableList=" + coursesForTimetableList +
                ", student=" + student +
                '}';
    }
}

