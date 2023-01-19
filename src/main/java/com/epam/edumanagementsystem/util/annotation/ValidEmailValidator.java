package com.epam.edumanagementsystem.util.annotation;

import com.epam.edumanagementsystem.config.MessageByLang;
import com.epam.edumanagementsystem.util.AppConstants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final Pattern PATTERN = Pattern.compile(AppConstants.EMAIL_PATTERN);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        String message = "";

        if (email.isBlank()) {
            message = MessageByLang.getMessage("FILL_INPUT_FIELD");
        } else if (!PATTERN.matcher(email).matches()) {
            message = MessageByLang.getMessage("INVALID_EMAIL");
        } else if (email.length() > AppConstants.FIELD_MAX_SIZE) {
            message = MessageByLang.getMessage("SYMBOLS_MAX_LENGTH");
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
