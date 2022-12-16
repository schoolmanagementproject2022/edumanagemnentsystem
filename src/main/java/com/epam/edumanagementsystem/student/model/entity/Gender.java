package com.epam.edumanagementsystem.student.model.entity;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String genderIdentity;

    Gender(String genderIdentity) {
        this.genderIdentity = genderIdentity;
    }

    public String getGenderIdentity() {
        return genderIdentity;
    }

}
