package com.epam.edumanagementsystem.util.annotation;

import com.epam.edumanagementsystem.config.MessageByLang;
import com.epam.edumanagementsystem.util.AppConstants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern PATTERN = Pattern.compile(AppConstants.PASSWORD_PATTERN);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        String message = "";

        if (password.isBlank()) {
            message = MessageByLang.getMessage("FILL_INPUT_FIELD");
        } else if (!PATTERN.matcher(password).matches()) {
            message = MessageByLang.getMessage("INVALID_PASSWORD");
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
