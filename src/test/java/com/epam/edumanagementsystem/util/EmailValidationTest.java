package com.epam.edumanagementsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidationTest {
    private List<String> invalidEmails;
    private List<String> validEmails;
    private List<String> blankEmails;
    private String PATTERN_MAIL;

    @BeforeEach
    void setUp() {
        validEmails = List.of("user@mail.ru", "user@ml.com", "u*s*er@mail.ru", "user@ma-il.ru", "user@mail.com", "USER@mail.ru",
                "user@MAIL.ru", "us@ma.ru", "37@mail.ru");
        invalidEmails = List.of("user.mail.ru", "user mailru", "$user@mail.com", "user*@mail.am", "us$er@mailru", "us$er@ru",
                "us$er@.ru", "us$*er@mail.com", "user@mail.r", "u@mail.ru", "user$mail.com", "user@*mail.ru", "user@ma&il.am", "user@mail.RU", "    @mail.ru");
        blankEmails = List.of("", " ");
        PATTERN_MAIL
                = "^([^! #$%&*+\\-/=?^_`{|\\.](?!.*[!#$%&*+-/=?^_`{|]{2})[a-zA-Z0-9!#$%&*+-/=?^_`{|]{0,50}[^! #$%&*+\\-/=?^_`{|\\.@]+)@([a-zA-Z0-9\\-]{1,50})\\.([a-z]{2,50})$";
    }

    @Test
    void emailsIsValid() {
        for (String validEmail : validEmails) {
            assertFalse(validEmail.isBlank());
            assertTrue(validEmail.matches(PATTERN_MAIL));
        }
    }

    @Test
    void emailsIsInvalid() {
        for (String invalidEmail : invalidEmails) {
            assertFalse(invalidEmail.isBlank());
            assertFalse(invalidEmail.matches(PATTERN_MAIL));
        }
    }

    @Test
    void emailIsBlank() {
        for (String blankEmail : blankEmails) {
            assertTrue(blankEmail.isBlank());
        }
    }
}