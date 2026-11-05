package com.example.demo.workschedule;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.partime.PartTimeEmployee;
import com.example.demo.partime.PartTimeRepository;
import com.example.demo.workschedule.dto.WorkRequestShift;
import com.example.demo.workschedule.dto.ApprovalRequest;
import com.example.demo.workschedule.dto.WorkScheduleDetailResponse;
import com.example.demo.workschedule.dto.WorkScheduleRequest;
import com.example.demo.workschedule.dto.WorkScheduleResponse;
import com.example.demo.workshift.WorkShift;
import com.example.demo.workshift.WorkShiftRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;
    private final WorkScheduleMapper workScheduleMapper;
    private final PartTimeRepository partTimeRepository;
    private final WorkShiftRepository workShiftRepository;
    private final EmployeeRepository employeeRepository;

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Jakarta");

    public WorkScheduleService(WorkScheduleRepository workScheduleRepository, WorkScheduleMapper workScheduleMapper,
            PartTimeRepository partTimeRepository, WorkShiftRepository workShiftRepository,
            EmployeeRepository employeeRepository) {
        this.workScheduleRepository = workScheduleRepository;
        this.workScheduleMapper = workScheduleMapper;
        this.partTimeRepository = partTimeRepository;
        this.workShiftRepository = workShiftRepository;
        this.employeeRepository = employeeRepository;
    }

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

    public List<WorkScheduleResponse> getSchedulesPendingApproval() {
        return workScheduleMapper.toResponseList(
                workScheduleRepository.findSchedulesPendingApproval());
    }

    public Optional<WorkScheduleDetailResponse> getApprovedScheduleDetailById(Integer id) {
        return workScheduleRepository.findApprovedScheduleById(id)
                .map(workScheduleMapper::toDetailResponse);
    }

    @Transactional
    public WorkScheduleResponse updateScheduleApproval(Integer id, ApprovalRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee authenticatedManager = employeeRepository.findByUser_WorkEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Manager not found"));

        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Work schedule not found"));

        workSchedule.setIsApproved(request.getIsApproved());
        workSchedule.setManager(authenticatedManager);

        return workScheduleMapper.toResponse(workScheduleRepository.save(workSchedule));
    }

    @Transactional
    public WorkScheduleResponse submitSchedule(WorkRequestShift request) {

        // 1. Ambil email user yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String workEmail = authentication.getName();

        // 2. Cari Part-Time Employee berdasarkan email login
        PartTimeEmployee partTimeEmployee = partTimeRepository.findByUser_WorkEmail(workEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Part-time employee not found"));

        // 3. Validasi tanggal pengajuan
        LocalDate workDate = request.getWorkDate();

        LocalDate today = LocalDate.now(ZONE_ID);

        LocalDate currentMonday = today.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        LocalDate nextFriday = currentMonday.plusWeeks(1).plusDays(4);

        if (workDate.isBefore(currentMonday)
                || workDate.isAfter(nextFriday)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Work date must be within this week or next week");
        }

        // 4. Pastikan tanggal bukan Sabtu atau Minggu
        if (workDate.getDayOfWeek() == DayOfWeek.SATURDAY
                || workDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Work date must be between Monday and Friday");
        }

        // 5. Cari shift berdasarkan ID dari request body
        WorkShift workShift = workShiftRepository.findById(request.getWorkShiftId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Work shift not found"));

        // 6. Tentukan rentang minggu pengajuan
        LocalDate weekMonday = workDate.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        LocalDate weekFriday = weekMonday.plusDays(4);

        // 7. Ambil jadwal aktif milik Part-Time Employee
        List<WorkSchedule> existingSchedules = workScheduleRepository.findActiveSchedulesByEmployeeAndDateRange(
                partTimeEmployee.getId(),
                weekMonday,
                weekFriday);

        // 8. Ambil jadwal pada tanggal yang sama
        List<WorkSchedule> sameDaySchedules = existingSchedules.stream()
                .filter(schedule -> schedule.getWorkDate().equals(workDate))
                .toList();

        // 9. Maksimal dua shift dalam satu hari
        if (sameDaySchedules.size() >= 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Maximum two shifts per day");
        }

        // 10. Jika sudah ada satu shift, shift baru harus bersebelahan
        if (!sameDaySchedules.isEmpty()) {

            WorkShift existingShift = sameDaySchedules.get(0).getWorkShift();

            boolean adjacent = existingShift.getEndTime().equals(workShift.getStartTime())
                    || workShift.getEndTime().equals(existingShift.getStartTime());

            if (!adjacent) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "The second shift must be adjacent to the existing shift");
            }
        }

        // 11. Maksimal lima shift dalam satu minggu
        if (existingSchedules.size() >= 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Maximum five shifts per week");
        }

        // 12. Maksimal 20 jam kerja dalam satu minggu
        long existingHours = existingSchedules.stream()
                .mapToLong(schedule -> calculateShiftHours(schedule.getWorkShift()))
                .sum();

        long requestedHours = calculateShiftHours(workShift);

        if (existingHours + requestedHours > 20) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Maximum 20 working hours per week");
        }

        // 13. Buat pengajuan jadwal baru
        WorkSchedule newSchedule = new WorkSchedule();

        newSchedule.setWorkDate(workDate);
        newSchedule.setPartTimeEmployee(partTimeEmployee);
        newSchedule.setWorkShift(workShift);

        // NULL berarti menunggu persetujuan Manager
        newSchedule.setIsApproved(null);

        // Manager belum ditentukan saat pengajuan
        newSchedule.setManager(null);

        // 14. Simpan ke database
        WorkSchedule savedSchedule = workScheduleRepository.save(newSchedule);

        return workScheduleMapper.toResponse(savedSchedule);
    }

    private long calculateShiftHours(WorkShift workShift) {

        LocalTime startTime = workShift.getStartTime();
        LocalTime endTime = workShift.getEndTime();

        Duration duration = Duration.between(startTime, endTime);

        // Mengakomodasi shift yang melewati tengah malam
        if (duration.isNegative() || duration.isZero()) {
            duration = duration.plusDays(1);
        }

        return duration.toHours();
    }

}
