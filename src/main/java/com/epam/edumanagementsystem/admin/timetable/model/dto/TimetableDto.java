package com.epam.edumanagementsystem.admin.timetable.model.dto;

import com.epam.edumanagementsystem.admin.model.entity.AcademicClass;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class TimetableDto {
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Please, select the date")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Please, select the date")
    private LocalDate endDate;

    private AcademicClass academicClass;

    public TimetableDto() {
    }

    public TimetableDto(Long id, LocalDate startDate, LocalDate endDate, AcademicClass academicClass) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academicClass = academicClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AcademicClass getAcademicClass() {
        return academicClass;
    }

    public void setAcademicClass(AcademicClass academicClass) {
        this.academicClass = academicClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimetableDto that = (TimetableDto) o;
        return Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(academicClass, that.academicClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, academicClass);
    }

    @Override
    public String toString() {
        return "TimetableDto{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", academicClass=" + academicClass +
                '}';
    }
}
