package com.example.demo.workschedule;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.workschedule.dto.WorkScheduleResponse;

@RestController
public class WorkScheduleController {
    private final WorkScheduleService workScheduleService;

    public WorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    // @GetMapping("/api/work-schedules/show-all-schedules")
    // public ResponseEntity<List<WorkScheduleResponse>> getAllWorkSchedules() {
    // List<WorkScheduleResponse> workScheduleResponses =
    // workScheduleService.getAllWorkSchedules();
    // return ResponseEntity.ok(workScheduleResponses);
    // }
    @GetMapping("/api/work-schedules/show-all-schedules")
    public ResponseEntity<List<WorkScheduleResponse>> getSchedules(
            @RequestParam(defaultValue = "THIS_WEEK") ScheduleWeekEnum week) {

        List<WorkScheduleResponse> schedules = workScheduleService.getSchedules(week);

        return ResponseEntity.ok(schedules);
    }
}
