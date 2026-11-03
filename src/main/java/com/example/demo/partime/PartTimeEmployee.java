package com.example.demo.partime;

import com.example.demo.user.User;
import com.example.demo.workschedule.WorkSchedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "part_time_employees")
public class PartTimeEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "personal_email", nullable = false, unique = true, length = 100)
    private String personalEmail;

    @Column(name = "personal_phone_number", nullable = false, unique = true, length = 20)
    private String personalPhoneNumber;

    @Column(name = "last_education", nullable = false, length = 50)
    private String lastEducation;

    @Column(name = "on_going_education", length = 50)
    private String onGoingEducation;

    @Column(name = "join_date", nullable = false, insertable = false, updatable = false)
    private LocalDateTime joinDate;

    @Column(name = "resign_date")
    private LocalDateTime resignDate;

    // Setiap PartTimeEmployee wajib memiliki satu User
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_email", referencedColumnName = "work_email", nullable = false)
    private User user;

    // PartTimeEmployee dapat memiliki banyak WorkSchedule
    @OneToMany(mappedBy = "partTimeEmployee")
    private List<WorkSchedule> workSchedules = new ArrayList<>();
}