package com.epam.edumanagementsystem.admin.timetable.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import com.epam.edumanagementsystem.admin.model.entity.AcademicCourse;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CoursesForTimetableDto {


    private Long id;

    @NotNull(message = "Please, fill the required fields")
    private String academicCourse;

    @NotNull(message = "Please, fill the required fields")
    private AcademicClass academicClass;

    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String dayOfWeek;


    private String status;

    public CoursesForTimetableDto() {
    }

    public CoursesForTimetableDto( String academicCourse, AcademicClass academicClass,
                                   String dayOfWeek, String status) {
        this.academicCourse = academicCourse;
        this.academicClass = academicClass;
        this.dayOfWeek = dayOfWeek;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcademicCourse() {
        return academicCourse;
    }

    public void setAcademicCourse(String academicCourse) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursesForTimetableDto that = (CoursesForTimetableDto) o;
        return academicCourse.equals(that.academicCourse) && academicClass.equals(that.academicClass) && dayOfWeek.equals(that.dayOfWeek) && status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(academicCourse, academicClass, dayOfWeek, status);
    }

    @Override
    public String toString() {
        return "CoursesForTimetableDto{" +
                "academicCourse=" + academicCourse +
                ", academicClass=" + academicClass +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
