package com.epam.edumanagementsystem.util;

import com.epam.edumanagementsystem.parent.model.dto.ParentDto;
import com.epam.edumanagementsystem.util.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    public static void checkMultipartFile(MultipartFile multipartFile, String status,
                                          Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            UserDataValidation.validateImage(multipartFile, model);
        }
        if (status.equals("validationFail")) {
            model.addAttribute("size", "File size exceeds maximum 2mb limit");
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
