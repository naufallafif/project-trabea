package com.example.demo.validator;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WordValidatorEducation implements ConstraintValidator<EducationWordValidation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Arrays.stream(EducationWord.values())
                .anyMatch(education -> education.getValue().equals(value));
    }
}
