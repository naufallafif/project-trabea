package com.example.demo.workschedule;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.partime.dto.PartTimeResponse;
import com.example.demo.workschedule.dto.WorkScheduleResponse;

@Service
public class WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;
    private final WorkScheduleMapper workScheduleMapper;

    public WorkScheduleService(WorkScheduleRepository workScheduleRepository, WorkScheduleMapper workScheduleMapper) {
        this.workScheduleRepository = workScheduleRepository;
        this.workScheduleMapper = workScheduleMapper;
    }

    // get all work schedules @GetMapping("/api/show-all-part-time")
    public List<WorkScheduleResponse> getAllWorkSchedules() {
        return workScheduleRepository.findAll()
                .stream()
                .map(workScheduleMapper::toResponse)
                .toList();
    }

    // get all work schedules by id
    // public Optional<WorkScheduleResponse> getWorkScheduleById(Integer id) {
    // return
    // workScheduleRepository.findById(id).map(workScheduleMapper::toResponse);
    // }

}
