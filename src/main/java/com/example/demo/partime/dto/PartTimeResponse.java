package com.example.demo.partime.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartTimeResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String personalEmail;
    private String personalPhoneNumber;
    private String lastEducation;
    private String onGoingEducation;
    private LocalDateTime joinDate;
    private LocalDateTime resignDate;
    private String workEmail;
}
