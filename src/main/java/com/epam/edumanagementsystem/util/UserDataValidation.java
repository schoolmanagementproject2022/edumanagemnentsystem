package com.epam.edumanagementsystem.util;

import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Objects;

@Component
public class UserDataValidation {
    @Lazy
    private static UserService userService;

    public UserDataValidation(UserService userService) {
        this.userService = userService;
    }

    private static final String PASSWORD_PATTERN = AppConstants.PASSWORD_PATTERN;

    private static final String PATTERN_EMAIL = AppConstants.EMAIL_PATTERN;

    public static void validatePassword(String password, Model model) {
        if (password.isBlank()) {
            model.addAttribute("blank", "Please, fill the required fields");
        } else if (!password.matches(PASSWORD_PATTERN)) {
            model.addAttribute("invalidPassword", "(Password length > 9, must contain" +
                    " upper and lower case, Latin symbols, numbers, and special characters");
        }
    }

    public static boolean validateEmail(String email) {
        if (!email.isBlank()) {
            if (!email.matches(PATTERN_EMAIL)) {
                return true;
            }
        }
        return false;
    }

    public static boolean existsEmail(String email) {
        if (!Objects.isNull(email)) {
            return userService.findAll().stream()
                    .anyMatch(userEmail -> userEmail.getEmail().equalsIgnoreCase(email));
        }
        return false;
    }

}
