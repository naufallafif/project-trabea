package com.example.demo.partime.dto;

import com.example.demo.validator.NumericOnly;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PartTimeUpdateRequest {
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    private String lastName;

    @NotNull(message = "Personal email cannot be null")
    @Email(message = "Email should be valid")
    private String personalEmail;

    @NotNull(message = "Personal phone number cannot be null")
    @Size(min = 10, max = 12, message = "Personal phone number must be between 10 and 12 characters")
    @NumericOnly(message = "Personal phone number must be numeric only")
    private String personalPhoneNumber;

    private String address;

    // TODO : add validator word Education (SD., SMP, SMA, D3, S1, S2, S3)
    @NotNull(message = "Last education cannot be null")
    @NotBlank(message = "Last education cannot be blank")
    private String lastEducation;

    private String onGoingEducation;
}
