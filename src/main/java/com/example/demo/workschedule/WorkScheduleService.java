package com.example.demo.workschedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.workschedule.dto.WorkScheduleResponse;

@Service
public class WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;
    private final WorkScheduleMapper workScheduleMapper;
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Jakarta");

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
    public List<WorkScheduleResponse> getSchedules(
            ScheduleWeekEnum week) {

        // 1. Mendapatkan tanggal hari ini
        LocalDate today = LocalDate.now(ZONE_ID);

        // 2. Menentukan hari Senin pada minggu berjalan
        LocalDate monday = today.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // 3. Menentukan minggu yang diminta
        if (week == ScheduleWeekEnum.NEXT_WEEK) {
            monday = monday.plusWeeks(1);
        }

        // 4. Menentukan hari Jumat
        LocalDate friday = monday.plusDays(4);

        // 5. Mengambil data dari database
        List<WorkSchedule> schedules = workScheduleRepository.findApprovedSchedules(
                monday,
                friday);

        // 6. Mengubah Entity menjadi Response DTO
        return workScheduleMapper.toResponseList(schedules);
    }
}
