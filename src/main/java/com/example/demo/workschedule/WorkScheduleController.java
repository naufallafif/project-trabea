package com.example.demo.workschedule;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.workschedule.dto.WorkRequestShift;
import com.example.demo.workschedule.dto.ApprovalRequest;
import com.example.demo.workschedule.dto.WorkScheduleDetailResponse;
import com.example.demo.workschedule.dto.WorkScheduleResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/work-schedules")
public class WorkScheduleController {
    private final WorkScheduleService workScheduleService;

    public WorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    @Operation(summary = "Access Manager dan Part Time")
    @GetMapping
    public ResponseEntity<List<WorkScheduleResponse>> getSchedules(
            @RequestParam(defaultValue = "THIS_WEEK") ScheduleWeekEnum week) {

        List<WorkScheduleResponse> schedules = workScheduleService.getSchedules(week);

        return ResponseEntity.ok(schedules);
    }

    @Operation(summary = "Manager: view schedules awaiting approval or rejected")
    @GetMapping("/pending-approval")
    public ResponseEntity<List<WorkScheduleResponse>> getSchedulesPendingApproval() {
        return ResponseEntity.ok(workScheduleService.getSchedulesPendingApproval());
    }

    @Operation(summary = "Access Manager")
    @GetMapping("/{id}")
    public ResponseEntity<WorkScheduleDetailResponse> getApprovedScheduleDetail(
            @PathVariable Integer id) {
        return workScheduleService.getApprovedScheduleDetailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Approve or reject a schedule (Manager only)")
    @PatchMapping("/{id}/approval")
    public ResponseEntity<WorkScheduleResponse> approveSchedule(
            @PathVariable Integer id,
            @Valid @RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(workScheduleService.updateScheduleApproval(id, request));
    }

    @Operation(summary = "Access Part Time")
    @PostMapping
    public ResponseEntity<WorkScheduleResponse> submitSchedule(
            @Valid @RequestBody WorkRequestShift request) {
        WorkScheduleResponse response = workScheduleService.submitSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
