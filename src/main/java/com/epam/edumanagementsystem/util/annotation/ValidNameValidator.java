package com.epam.edumanagementsystem.util.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidNameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        String message = "";
        if (name.isBlank() || name.isEmpty()) {
            message = "Please, fill the required fields";
        } else if (name.length() > 50) {
            message = "Symbols can't be more than 50";
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
