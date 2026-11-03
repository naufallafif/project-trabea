package com.example.demo.workshift;

import com.example.demo.workschedule.WorkSchedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "work_shifts")
public class WorkShift {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // Satu WorkShift dapat digunakan oleh banyak WorkSchedule
    @OneToMany(mappedBy = "workShift")
    private List<WorkSchedule> workSchedules = new ArrayList<>();
}
