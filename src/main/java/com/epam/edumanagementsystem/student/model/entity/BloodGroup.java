package com.epam.edumanagementsystem.student.model.entity;

public enum BloodGroup {
    A_PLUS("A+"),
    A_MINUS("A-"),
    B_PLUS("B+"),
    B_MINUS("B-"),
    O_PLUS("O+"),
    O_MINUS("O-"),
    AB_PLUS("AB+"),
    AB_MINUS("AB-");

    private final String bloodGroup;

    BloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

}







