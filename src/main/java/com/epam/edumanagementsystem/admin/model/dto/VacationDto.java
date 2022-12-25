package com.epam.edumanagementsystem.admin.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.EMPTY_FIELD;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.DATE_FORMATTER;

public class VacationDto {
    private Long id;

    @NotNull(message = EMPTY_FIELD)
    @DateTimeFormat(pattern = DATE_FORMATTER)
    private LocalDate startDate;

    @NotNull(message = EMPTY_FIELD)
    @DateTimeFormat(pattern = DATE_FORMATTER)
    private LocalDate endDate;

    public VacationDto(Long id, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public VacationDto() {

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
        VacationDto that = (VacationDto) o;
        return Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate);
    }

    @Override
    public String toString() {
        return "VacationDto{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
