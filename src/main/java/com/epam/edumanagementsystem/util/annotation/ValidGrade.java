package com.epam.edumanagementsystem.util.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ValidGradeValidator.class})
public @interface ValidGrade {
    String message() default "Only 1-100 numbers are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
