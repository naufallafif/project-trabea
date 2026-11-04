package com.example.demo.workschedule.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkRequestShift {
    @NotNull(message = "Work date cannot be null")
    private LocalDate workDate;

    @NotNull(message = "Work shift ID cannot be null")
    private Integer workShiftId;
}
