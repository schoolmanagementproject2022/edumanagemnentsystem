package com.epam.edumanagementsystem.admin.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "academicYear")
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy")
    @NotNull(message = "Please, fill the required fields")
    private int startDate;

    @DateTimeFormat(pattern = "yyyy")
    @NotNull(message = "Please, fill the required fields")
    private int endDate;

    public AcademicYear() {
    }

    public AcademicYear(Long id, int startDate, int endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicYear that = (AcademicYear) o;
        return startDate == that.startDate && endDate == that.endDate && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate);
    }

    @Override
    public String toString() {
        return "AcademicYear{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
