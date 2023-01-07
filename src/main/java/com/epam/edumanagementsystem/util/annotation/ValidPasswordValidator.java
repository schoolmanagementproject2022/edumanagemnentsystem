package com.epam.edumanagementsystem.util.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern PATTERN =
            Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&])[A-Za-z\\\\d()`~@$?!\\\"'^#*:.,;<>%-_+=|/{}&]{9,50}");

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        String message = "";
        if (password.isBlank()) {
            message = "Please, fill the required fields";
        } else if (!PATTERN.matcher(password).matches()) {
            message = "Password is invalid";
        }
        if (!message.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        } else {
            return true;
        }
        return false;
    }
}
