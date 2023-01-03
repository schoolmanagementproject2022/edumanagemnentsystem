package com.epam.edumanagementsystem.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final Pattern PATTERN =
            Pattern.compile("^([^! #$%&*+\\-/=?^_`{|\\.](?!.*[!#$%&*+-/=?^_`{|]{2})[a-zA-Z0-9!#$%&*+-/=?^_`{|]{0,50}[^! #$%&*+\\-/=?^_`{|\\.@]+)@([a-zA-Z0-9\\-]{1,50})\\.([a-z]{2,50})$");

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        String message = "";
        if (email.isBlank()) {
            message = "Please, fill the required fields";
        } else if (!PATTERN.matcher(email).matches()) {
            message = "Email is invalid";
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
