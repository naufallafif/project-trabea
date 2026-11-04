package com.example.demo.workschedule;

import com.example.demo.partime.PartTimeEmployee;
import com.example.demo.workschedule.dto.ApprovalRequest;
import com.example.demo.workschedule.dto.WorkScheduleRequest;
import com.example.demo.workschedule.dto.WorkScheduleResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkScheduleMapper {
        // Create
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "isApproved", ignore = true)
        @Mapping(target = "manager", ignore = true)
        @Mapping(target = "partTimeEmployee", ignore = true)
        @Mapping(target = "workShift", ignore = true)
        WorkSchedule toEntity(WorkScheduleRequest request);

        // Update schedule request
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "isApproved", ignore = true)
        @Mapping(target = "manager", ignore = true)
        @Mapping(target = "partTimeEmployee", ignore = true)
        @Mapping(target = "workShift", ignore = true)
        void updateEntity(
                        WorkScheduleRequest request,
                        @MappingTarget WorkSchedule workSchedule);

        // Update approval
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "workDate", ignore = true)
        @Mapping(target = "manager", ignore = true)
        @Mapping(target = "partTimeEmployee", ignore = true)
        @Mapping(target = "workShift", ignore = true)
        void updateApproval(
                        ApprovalRequest request,
                        @MappingTarget WorkSchedule workSchedule);

        // Response
        @Mapping(source = "manager.id", target = "managerId")
        @Mapping(source = "partTimeEmployee.id", target = "partTimeEmployeeId")
        @Mapping(source = "workShift.id", target = "workShiftId")
        @Mapping(target = "fullName", expression = "java(workSchedule.getPartTimeEmployee().getFirstName() + \" \" + workSchedule.getPartTimeEmployee().getLastName())")
        WorkScheduleResponse toResponse(WorkSchedule workSchedule);
}