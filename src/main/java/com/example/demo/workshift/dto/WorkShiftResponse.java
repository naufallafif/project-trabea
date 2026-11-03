package com.example.demo.workshift.dto;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkShiftResponse {

    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
}
