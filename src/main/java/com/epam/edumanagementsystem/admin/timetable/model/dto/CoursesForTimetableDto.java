package com.epam.edumanagementsystem.admin.timetable.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Objects;

public class CoursesForTimetableDto {

    private Long id;

    @NotBlank(message = "Please, fill the required fields")
    private AcademicCourse academicCourse;
    @NotBlank(message = "Please, fill the required fields")
    private AcademicClass academicClass;

    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String dayOfWeek;

    public CoursesForTimetableDto() {
    }

    public CoursesForTimetableDto(AcademicCourse academicCourse, AcademicClass academicClass, String dayOfWeek) {
        this.academicCourse = academicCourse;
        this.academicClass = academicClass;
        this.dayOfWeek = dayOfWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AcademicCourse getAcademicCourse() {
        return academicCourse;
    }

    public void setAcademicCourse(AcademicCourse academicCourse) {
        this.academicCourse = academicCourse;
    }

    public AcademicClass getAcademicClass() {
        return academicClass;
    }

    public void setAcademicClass(AcademicClass academicClass) {
        this.academicClass = academicClass;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursesForTimetableDto that = (CoursesForTimetableDto) o;
        return Objects.equals(academicCourse, that.academicCourse) && Objects.equals(academicClass, that.academicClass) && Objects.equals(dayOfWeek, that.dayOfWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(academicCourse, academicClass, dayOfWeek);
    }

    @Override
    public String toString() {
        return "CoursesForTimetableDto{" +
                "academicCourse=" + academicCourse +
                ", academicClass=" + academicClass +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }
}
