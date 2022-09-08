package com.epam.edumanagementsystem.admin.model.dto;

import java.util.Objects;

public class AcademicClassDto {
    private Long id;
    private String classNumber;

    public AcademicClassDto(){

    }
    public AcademicClassDto(Long id, String classNumber) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClassDto that = (AcademicClassDto) o;
        return Objects.equals(id, that.id) && Objects.equals(classNumber, that.classNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classNumber);
    }

    @Override
    public String toString() {
        return "AcademicClassDto{" +
                "id=" + id +
                ", classNumber='" + classNumber + '\'' +
                '}';
    }
}
