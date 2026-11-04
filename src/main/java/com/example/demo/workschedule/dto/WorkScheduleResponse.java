package com.example.demo.workschedule.dto;

import java.time.LocalDate;

public record WorkScheduleResponse(
        Integer id,
        Integer partTimeEmployeeId,
        String fullName,
        LocalDate workDate,
        Integer workShiftId,
        Integer managerId) {
}