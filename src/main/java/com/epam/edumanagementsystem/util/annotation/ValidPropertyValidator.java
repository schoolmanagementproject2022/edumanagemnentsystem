package com.epam.edumanagementsystem.util.annotation;

import com.epam.edumanagementsystem.config.MessageByLang;
import com.epam.edumanagementsystem.util.AppConstants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidPropertyValidator implements ConstraintValidator<ValidProperty, String> {


    private static final Pattern PATTERN = Pattern.compile(AppConstants.PROPERTY_PATTERN);

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        String message = "";

        if (name.isBlank() || name.isEmpty()) {
            message = MessageByLang.getMessage("FILL_INPUT_FIELD");
        } else if (name.length() > AppConstants.FIELD_MAX_SIZE) {
            message = MessageByLang.getMessage("SYMBOLS_MAX_LENGTH");
        } else if (!PATTERN.matcher(name).matches()) {
            message = MessageByLang.getMessage("VALID_SYMBOLS");
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
