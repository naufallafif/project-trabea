package com.example.demo.workschedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//by id
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Integer> {
        @Query("""
                        SELECT ws
                        FROM WorkSchedule ws
                        JOIN FETCH ws.partTimeEmployee p
                        JOIN FETCH ws.workShift s
                        WHERE ws.isApproved IS NULL OR ws.isApproved = false
                        ORDER BY ws.workDate ASC, s.id ASC
                        """)
        List<WorkSchedule> findSchedulesPendingApproval();

        @Query("""
                        SELECT ws
                        FROM WorkSchedule ws
                        JOIN FETCH ws.partTimeEmployee p
                        JOIN FETCH p.user
                        WHERE ws.id = :id
                        AND ws.isApproved = true
                        """)
        Optional<WorkSchedule> findApprovedScheduleById(@Param("id") Integer id);

        // query is approved nya true
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

        // query pengajuan shift
        @Query("""
                        SELECT ws
                        FROM WorkSchedule ws
                        JOIN FETCH ws.workShift
                        WHERE ws.partTimeEmployee.id = :partTimeEmployeeId
                        AND ws.workDate BETWEEN :startDate AND :endDate
                        AND (ws.isApproved IS NULL OR ws.isApproved = true)
                        ORDER BY ws.workDate ASC
                        """)
        List<WorkSchedule> findActiveSchedulesByEmployeeAndDateRange(
                        @Param("partTimeEmployeeId") Integer partTimeEmployeeId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        List<WorkSchedule> findByPartTimeEmployee_IdOrderByWorkDateDesc(Integer partTimeEmployeeId);
}
