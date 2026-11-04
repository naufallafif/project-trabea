package com.example.demo.partime;

import com.example.demo.partime.dto.PartTimeRequest;
import com.example.demo.partime.dto.PartTimeResponse;
// import com.example.demo.partime.PartTimeEmployee;
import com.example.demo.partime.dto.PartTimeUpdateRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PartTimeMapper {

    // Create
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "joinDate", ignore = true)
    @Mapping(target = "resignDate", ignore = true)
    PartTimeEmployee toEntity(PartTimeRequest request);

    // Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "joinDate", ignore = true)
    @Mapping(target = "resignDate", ignore = true)
    void updateEntity(
            PartTimeUpdateRequest request,
            @MappingTarget PartTimeEmployee partTimeEmployee);

    // Response
    @Mapping(target = "fullName", expression = "java(partTimeEmployee.getFirstName() + \" \" + partTimeEmployee.getLastName())")
    @Mapping(source = "user.workEmail", target = "workEmail")
    PartTimeResponse toResponse(PartTimeEmployee partTimeEmployee);
}