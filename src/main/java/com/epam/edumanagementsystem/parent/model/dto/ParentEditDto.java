package com.epam.edumanagementsystem.parent.model.dto;

import com.epam.edumanagementsystem.util.AppConstants;
import com.epam.edumanagementsystem.util.validation.ValidEmail;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Objects;

public class ParentEditDto {

    private Long id;

    @NotBlank(message = "{EMPTY_FIELD}")
    @Size(max = AppConstants.FIELD_MAX_SIZE, message = "{SYMBOLS_MAX_LENGTH}")
    private String name;

    @NotBlank(message = "{EMPTY_FIELD}")
    @Size(max = AppConstants.FIELD_MAX_SIZE, message = "{SYMBOLS_MAX_LENGTH}")
    private String surname;

    @ValidEmail
    private String email;

    private String picUrl;

    public ParentEditDto() {

    }

    public ParentEditDto(Long id, String name, String surname, String email, String picUrl) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.picUrl = picUrl;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentEditDto that = (ParentEditDto) o;
        return id.equals(that.id) && name.equals(that.name) && surname.equals(that.surname) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email);
    }

    @Override
    public String toString() {
        return "ParentEditDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

