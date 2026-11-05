package com.example.demo.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.demo.partime.dto.PartTimeRequest;

import jakarta.validation.Validation;

class EducationWordValidationTest {
    @Test
    void acceptsEveryEducationWordEnumValue() {
        try (var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            var validator = validatorFactory.getValidator();

            for (EducationWord education : EducationWord.values()) {
                var request = new PartTimeRequest();
                request.setLastEducation(education.getValue());

                assertFalse(validator.validateProperty(request, "lastEducation").stream()
                        .anyMatch(violation -> violation.getConstraintDescriptor().getAnnotation()
                                .annotationType() == EducationWordValidation.class));
            }
        }
    }

    @Test
    void rejectsUnsupportedEducationWord() {
        try (var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            var validator = validatorFactory.getValidator();
            var request = new PartTimeRequest();
            request.setLastEducation("Something else");

            assertTrue(validator.validateProperty(request, "lastEducation").stream()
                    .anyMatch(violation -> violation.getConstraintDescriptor().getAnnotation()
                            .annotationType() == EducationWordValidation.class));
        }
    }
}
