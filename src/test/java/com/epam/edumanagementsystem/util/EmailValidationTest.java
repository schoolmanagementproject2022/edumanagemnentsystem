package com.epam.edumanagementsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidationTest {
    private List<String> invalidEmails;
    private List<String> validEmails;

    @BeforeEach
    void setUp() {
        validEmails = List.of("user@mail.ru", "user@ml.com", "u*s*er@mail.ru", "user@ma-il.ru", "user@mail.com", "USER@mail.ru",
                "user@MAIL.ru", "us@ma.ru", "37@mail.ru");
        invalidEmails = List.of("user.mail.ru", "usermailru", "$user@mail.com", "user*@mail.am", "us$er@mailru", "us$er@ru",
                "us$er@.ru", "us$*er@mail.com", "user@mail.r", "u@mail.ru", "user$mail.com", "user@*mail.ru", "user@ma&il.am", "user@mail.RU");
    }

    @Test
    void emailsIsValid() {
        for (String validEmail : validEmails) {
            assertTrue(EmailValidation.validate(validEmail));
        }
    }

    @Test
    void emailsIsInvalid() {
        for (String invalidEmail : invalidEmails) {
            assertFalse(EmailValidation.validate(invalidEmail));
        }
    }
}