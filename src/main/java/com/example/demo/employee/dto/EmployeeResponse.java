package com.example.demo.employee.dto;


public record EmployeeResponse(Integer id,
        String firstName,
        String lastName,
        String workEmail) {
}