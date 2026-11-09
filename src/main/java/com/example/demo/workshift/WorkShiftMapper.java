package com.example.demo.workshift;

import com.example.demo.workshift.dto.WorkShiftRequest;
import com.example.demo.workshift.dto.WorkShiftResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkShiftMapper {

    // Create
    WorkShift toEntity(WorkShiftRequest request);

    // Update
    @Mapping(target = "id", ignore = true)
    void updateEntity(
            WorkShiftRequest request,
            @MappingTarget WorkShift workShift);

    // Response
    WorkShiftResponse toResponse(WorkShift workShift);
}