package com.example.demo.workschedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Integer> {

    @Query("""
            SELECT ws
            FROM WorkSchedule ws
            JOIN FETCH ws.partTimeEmployee p
            JOIN FETCH ws.workShift s
            WHERE ws.workDate BETWEEN :startDate AND :endDate
            AND ws.isApproved = true
            ORDER BY ws.workDate ASC, s.id ASC
            """)
    List<WorkSchedule> findApprovedSchedules(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
