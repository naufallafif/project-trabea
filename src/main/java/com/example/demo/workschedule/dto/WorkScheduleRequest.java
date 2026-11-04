package com.example.demo.workschedule.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkScheduleRequest {
    private Integer partTimeEmployeeId;
    private String fullName;
    private LocalDate workDate;
    private Integer workShiftId;
}
