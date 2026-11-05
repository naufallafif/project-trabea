package com.example.demo.partime.dto;

import com.example.demo.validator.EducationWordValidation;
import com.example.demo.validator.NumericOnly;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PartTimeRequest {
    @Size(min = 1, max = 50, message = "Full name must be between 1 and 50 characters")
    @NotNull(message = "Full name cannot be null")
    @NotBlank(message = "Full name cannot be blank")
    private String fullName;
    @NotNull(message = "Personal email cannot be null")
    @Email(message = "Email should be valid")
    private String personalEmail;

    @NotNull(message = "Personal phone number cannot be null")
    @Size(min = 10, max = 12, message = "Personal phone number must be between 10 and 12 characters")
    @NumericOnly(message = "Personal phone number must be numeric only")
    private String personalPhoneNumber;

    @NotNull(message = "Last education cannot be null")
    @NotBlank(message = "Last education cannot be blank")
    @EducationWordValidation
    private String lastEducation;

    private String onGoingEducation;
}
