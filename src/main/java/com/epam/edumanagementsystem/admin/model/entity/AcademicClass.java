package com.epam.edumanagementsystem.admin.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name ="academicClass", schema = "public")
public class AcademicClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 2, message = "Size is not valid!")
    private String classNumber;

    public AcademicClass(Long id, String classNumber) {
        this.id = id;
        this.classNumber = classNumber;
    }

    public AcademicClass() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public Long getId() {
        return id;
    }

    public String getClassNumber() {
        return classNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClass that = (AcademicClass) o;
        return Objects.equals(id, that.id) && Objects.equals(classNumber, that.classNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classNumber);
    }

    @Override
    public String toString() {
        return "AcademicClass{" +
                "id=" + id +
                ", classNumber='" + classNumber + '\'' +
                '}';
    }
}
