package com.example.demo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NumericOnly {
    String message() default "must be numeric only";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
