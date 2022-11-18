package com.epam.edumanagementsystem.util;

import org.springframework.ui.Model;

public class PasswordValidation {
    private static final String PASSWORD_PATTERN
            = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&])[A-Za-z\\\\d()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&]{9,50}";

    public static void validatePassword(String password, Model model) {
        if (password.isBlank()) {
            model.addAttribute("blank", "Please, fill the required fields");
        } else if (!password.matches(PASSWORD_PATTERN)) {
            model.addAttribute("invalidPassword", "(Password length > 9, must contain" +
                    " upper and lower case, Latin symbols, numbers, and special characters");
        }
    }
}
