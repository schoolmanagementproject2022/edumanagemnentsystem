package com.epam.edumanagementsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTest {
    private List<String> invalidPasswords;
    private List<String> validPasswords;
    private List<String> blankPasswords;
    private String PASSWORD_PATTERN;

    @BeforeEach
    void setUp() {
        validPasswords = List.of("1pA#6mG%5qE@", "2wT&1rU)7uY@", "4mZ)5rX(5gW(", "0dJ(7eD@2qE)",
                "6uP&6jV$2qP%", "8sN*8pT&7nH(");
        invalidPasswords = List.of("a", "Aa2#", "123456789", "aSdFgHkjKl", "1H23h4H5", "H+G&f=d*TJ");
        blankPasswords = List.of("", " ");
        PASSWORD_PATTERN
                = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&])[A-Za-z\\\\d()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&]{9,50}";
    }

    @Test
    void passwordIsValid() {
        for (String validPassword : validPasswords) {
            assertTrue(validPassword.matches(PASSWORD_PATTERN));
        }
    }

    @Test
    void passwordIsNotBlank() {
        for (String validPassword : validPasswords) {
            assertFalse(validPassword.isBlank());
        }
    }

    @Test
    void passwordIsInvalid() {
        for (String invalidPassword : invalidPasswords) {
            assertFalse(invalidPassword.matches(PASSWORD_PATTERN));
        }
    }

    @Test
    void passwordIsBlank() {
        for (String blankPassword : blankPasswords) {
            assertTrue(blankPassword.isBlank());
        }
    }
}