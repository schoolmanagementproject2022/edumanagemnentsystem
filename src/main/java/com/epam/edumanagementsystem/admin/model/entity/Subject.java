package com.epam.edumanagementsystem.admin.model.entity;

import com.epam.edumanagementsystem.teacher.model.entity.Teacher;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subject")

public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please, fill the required fields")
    @Size(max = 50, message = "Symbols can't be more than 50")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "subject_teacher_mapping",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teacherSet = new HashSet<>();

    public Subject(Long id, String username, Set<Teacher> teacherSet) {
        this.id = id;
        this.name = username;
        this.teacherSet = teacherSet;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }


    public Subject() {
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

    public void setName(String username) {
        this.name = username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacherSet);
    }
}
