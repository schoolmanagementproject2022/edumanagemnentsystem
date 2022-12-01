package com.epam.edumanagementsystem.util;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

public class Validation {

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

    public static void validateImage(MultipartFile multipartFile, Model model) throws IOException {
        if (multipartFile.getBytes().length > 2097152) {
            model.addAttribute("size", "File size exceeds maximum 2mb limit");
        }
        if (!Objects.requireNonNull(multipartFile.getContentType()).equals("image/jpg")
                && !multipartFile.getContentType().equals("image/jpeg")
                && !multipartFile.getContentType().equals("image/png")) {
            model.addAttribute("formatValidationMessage", "Only PNG, JPEG and JPG files are allowed.");
        }
    }
}
