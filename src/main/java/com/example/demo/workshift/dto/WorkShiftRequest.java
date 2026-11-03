package com.example.demo.workshift.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkShiftRequest {

    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
}
