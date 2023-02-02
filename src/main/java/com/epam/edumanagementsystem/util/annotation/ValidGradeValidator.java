package com.epam.edumanagementsystem.util.annotation;

import com.epam.edumanagementsystem.config.MessageByLang;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidGradeValidator implements ConstraintValidator<ValidGrade, String> {

    @Override
    public boolean isValid(String grade, ConstraintValidatorContext context) {
        String message = "";

        if (grade.isBlank()) {
            return true;
        }
        int intGrade = Integer.parseInt(grade);
        if (intGrade > 100 || intGrade < 1) {
            message = MessageByLang.getMessage("INVALID_GRADE");
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
