package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumericOnlyValidator implements ConstraintValidator<NumericOnly, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !value.isEmpty() && value.chars().allMatch(Character::isDigit);
    }

}
