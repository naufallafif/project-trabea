package com.example.demo.workschedule;

import com.example.demo.workschedule.dto.ApprovalRequest;
import com.example.demo.workschedule.dto.WorkScheduleDetailResponse;
import com.example.demo.workschedule.dto.WorkScheduleRequest;
import com.example.demo.workschedule.dto.WorkScheduleResponse;

import java.util.List;

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

        @Mapping(target = "managerId", source = "manager.id")
        @Mapping(target = "partTimeEmployeeId", source = "partTimeEmployee.id")
        @Mapping(target = "partTimeEmployeeName", expression = "java(entity.getPartTimeEmployee().getFirstName() + \" \" + entity.getPartTimeEmployee().getLastName())")
        @Mapping(target = "workShiftId", source = "workShift.id")
        WorkScheduleResponse toResponse(WorkSchedule entity);

        List<WorkScheduleResponse> toResponseList(
                        List<WorkSchedule> entities);

        @Mapping(target = "fullName", expression = "java(entity.getPartTimeEmployee().getFirstName() + \" \" + entity.getPartTimeEmployee().getLastName())")
        @Mapping(target = "workEmail", source = "partTimeEmployee.user.workEmail")
        @Mapping(target = "personalEmail", source = "partTimeEmployee.personalEmail")
        @Mapping(target = "personalPhoneNumber", source = "partTimeEmployee.personalPhoneNumber")
        WorkScheduleDetailResponse toDetailResponse(WorkSchedule entity);

}