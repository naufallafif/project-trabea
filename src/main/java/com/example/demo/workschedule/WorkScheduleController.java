package com.example.demo.workschedule;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.workschedule.dto.WorkScheduleResponse;

@RestController
public class WorkScheduleController {
    private final WorkScheduleService workScheduleService;

    public WorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    @GetMapping("/api/work-schedules/show-all-schedules")
    public ResponseEntity<List<WorkScheduleResponse>> getAllWorkSchedules() {
        List<WorkScheduleResponse> workScheduleResponses = workScheduleService.getAllWorkSchedules();
        return ResponseEntity.ok(workScheduleResponses);
    }
}
