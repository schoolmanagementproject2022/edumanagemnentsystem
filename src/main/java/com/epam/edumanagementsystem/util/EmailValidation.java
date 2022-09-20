package com.epam.edumanagementsystem.util;

public class EmailValidation {

    private static final String PATTERN_MAIL
            = "[^@]+@[^@]+\\.[a-zA-Z]{2,3}$";

    public static boolean validate(String email) {
        return email.matches(PATTERN_MAIL);
    }
}