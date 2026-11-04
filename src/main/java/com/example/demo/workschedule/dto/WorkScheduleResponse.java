package com.example.demo.workschedule.dto;

import java.time.LocalDate;

public record WorkScheduleResponse(
        Integer id,
        LocalDate workDate,
        Boolean isApproved,
        Integer managerId,
        Integer partTimeEmployeeId,
        String partTimeEmployeeName,
        Integer workShiftId) {
}