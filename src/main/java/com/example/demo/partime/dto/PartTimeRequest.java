package com.example.demo.partime.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartTimeRequest {

    private String firstName;
    private String lastName;
    private String personalEmail;
    private String personalPhoneNumber;
    private String lastEducation;
    private String onGoingEducation;
    private String workEmail;
}
