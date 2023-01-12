package com.epam.edumanagementsystem.admin.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.EMPTY_FIELD;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.DATE_FORMATTER;

public class AcademicYearDto {

    private Long id;

    @DateTimeFormat(pattern = DATE_FORMATTER)
    @NotNull(message = EMPTY_FIELD)
    private LocalDate startDate;

    @DateTimeFormat(pattern = DATE_FORMATTER)
    @NotNull(message = EMPTY_FIELD)
    private LocalDate endDate;

    public AcademicYearDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicYearDto that = (AcademicYearDto) o;
        return Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate);
    }

    @Override
    public String toString() {
        return "AcademicYearDto{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
