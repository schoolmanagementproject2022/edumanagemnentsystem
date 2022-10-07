package com.epam.edumanagementsystem.admin.model.entity;

import com.epam.edumanagementsystem.admin.timetable.model.entity.CoursesForTimetable;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "academicClass")
public class AcademicClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "Symbols can't be more than 50")
    @Column(unique = true)
    @NotBlank(message = "Please, fill the required fields")
    private String classNumber;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "academicClass_teacher_mapping",
            joinColumns = @JoinColumn(name = "academicClass_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teacher;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "academicClass_academicCourse_mapping",
            joinColumns = @JoinColumn(name = "academicClass_id"),
            inverseJoinColumns = @JoinColumn(name = "academicCourse_id"))
    private Set<AcademicCourse> academicCourseSet;

    @ManyToMany
    private List<CoursesForTimetable> coursesForTimetableList = new ArrayList<>();


    public AcademicClass(Long id, String classNumber, Set<Teacher> teacher, Set<AcademicCourse> academicCourseSet) {
        this.id = id;
        this.classNumber = classNumber;
        this.teacher = teacher;
        this.academicCourseSet = academicCourseSet;
    }

    public Set<AcademicCourse> getAcademicCourseSet() {
        return academicCourseSet;
    }

    public void setAcademicCourseSet(Set<AcademicCourse> academicCourseSet) {
        this.academicCourseSet = academicCourseSet;
    }

    public List<CoursesForTimetable> getCoursesForTimetableList() {
        return coursesForTimetableList;
    }

    public void setCoursesForTimetableList(List<CoursesForTimetable> coursesForTimetableList) {
        this.coursesForTimetableList = coursesForTimetableList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClass that = (AcademicClass) o;
        return Objects.equals(id, that.id) && Objects.equals(classNumber, that.classNumber) && Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classNumber, teacher);
    }

    @Override
    public String toString() {
        return "AcademicClass{" +
                "id=" + id +
                ", classNumber='" + classNumber + '\'' +
                ", teacher=" + teacher +
                ", academicCourseSet=" + academicCourseSet +
                ", coursesForTimetableList=" + coursesForTimetableList +
                '}';
    }
}
