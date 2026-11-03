package com.example.demo.employee.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String workEmail;
}
