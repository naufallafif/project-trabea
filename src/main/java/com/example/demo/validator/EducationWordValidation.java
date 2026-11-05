package com.example.demo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WordValidatorEducation.class)
public @interface EducationWordValidation {
    String message() default "Last education must be one of the supported education levels";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
