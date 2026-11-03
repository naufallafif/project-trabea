package com.example.demo.employee;

import com.example.demo.employee.dto.EmployeeRequest;
import com.example.demo.employee.dto.EmployeeResponse;
// import com.example.demo.employee.Employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Create
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Employee toEntity(EmployeeRequest request);

    // Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(
            EmployeeRequest request,
            @MappingTarget Employee employee);

    // Response
    @Mapping(source = "user.workEmail", target = "workEmail")
    EmployeeResponse toResponse(Employee employee);
}