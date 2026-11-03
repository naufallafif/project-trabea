package com.example.demo.workschedule.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkScheduleResponse {

    private Integer id;
    private LocalDate workDate;
    private Boolean isApproved;
    private Integer managerId;
    private Integer partTimeEmployeeId;
    private Integer workShiftId;
}
