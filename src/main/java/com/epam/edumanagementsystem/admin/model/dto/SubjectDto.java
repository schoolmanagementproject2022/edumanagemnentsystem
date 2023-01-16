package com.epam.edumanagementsystem.admin.model.dto;

import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import com.epam.edumanagementsystem.util.annotation.ValidProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.EMPTY_FIELD;
import static com.epam.edumanagementsystem.admin.constants.ExceptionMessages.SYMBOL_LENGTH;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.FIELD_MAX_SIZE;
import static com.epam.edumanagementsystem.admin.constants.GlobalConstants.USED_SYMBOLS;

public class SubjectDto {

    private Long id;

    @ValidProperty
    private String name;

    private Set<Teacher> teacherSet = new HashSet<>();

    public SubjectDto(Long id, String name, Set<Teacher> teacherSet) {
        this.id = id;
        this.name = name;
        this.teacherSet = teacherSet;
    }

    public SubjectDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDto that = (SubjectDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(teacherSet, that.teacherSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacherSet);
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherSet=" + teacherSet +
                '}';
    }
}
