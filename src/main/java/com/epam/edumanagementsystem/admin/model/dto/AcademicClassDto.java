package com.epam.edumanagementsystem.admin.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import com.epam.edumanagementsystem.student.model.entity.Student;
import com.epam.edumanagementsystem.teacher.model.entity.Teacher;

import java.util.Objects;
import java.util.Set;

public class AcademicClassDto {
    private Long id;
    private String classNumber;

    private Set<AcademicCourse> academicCourse;

    private Set<Teacher> teacherSet;

    private Teacher classroomTeacher;

    private Set<Student> students;

    public AcademicClassDto() {
    }

    public AcademicClassDto(Long id, String classNumber, Set<AcademicCourse> academicCourse, Set<Teacher> teacherSet, Teacher classroomTeacher, Set<Student> students) {
        this.id = id;
        this.classNumber = classNumber;
        this.academicCourse = academicCourse;
        this.teacherSet = teacherSet;
        this.classroomTeacher = classroomTeacher;
        this.students = students;
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

    public Set<AcademicCourse> getAcademicCourse() {
        return academicCourse;
    }

    public void setAcademicCourse(Set<AcademicCourse> academicCourse) {
        this.academicCourse = academicCourse;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }

    public Teacher getClassroomTeacher() {
        return classroomTeacher;
    }

    public void setClassroomTeacher(Teacher classroomTeacher) {
        this.classroomTeacher = classroomTeacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClassDto that = (AcademicClassDto) o;
        return Objects.equals(id, that.id) && Objects.equals(classNumber, that.classNumber) && Objects.equals(academicCourse, that.academicCourse) && Objects.equals(teacherSet, that.teacherSet) && Objects.equals(classroomTeacher, that.classroomTeacher) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classNumber, academicCourse, teacherSet, classroomTeacher, students);
    }

    @Override
    public String toString() {
        return "AcademicClassDto{" +
                "id=" + id +
                ", classNumber='" + classNumber + '\'' +
                ", academicCourse=" + academicCourse +
                ", teacherSet=" + teacherSet +
                ", classroomTeacher=" + classroomTeacher +
                ", students=" + students +
                '}';
    }
}
