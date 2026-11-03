package com.example.demo.employee;

import com.example.demo.user.User;
import com.example.demo.workschedule.WorkSchedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    // Setiap Employee wajib memiliki satu User
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_email", referencedColumnName = "work_email", nullable = false)
    private User user;

    // Employee dapat menjadi manager pada banyak WorkSchedule
    @OneToMany(mappedBy = "manager")
    private List<WorkSchedule> managedSchedules = new ArrayList<>();
}