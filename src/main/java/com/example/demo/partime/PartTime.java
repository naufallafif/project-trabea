package com.example.demo.partime;

import com.example.demo.user.User;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "part_time_employees")
@Getter
@Setter
public class PartTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 25, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "personal_email", length = 100, nullable = false, unique = true)
    private String personalEmail;

    @Column(name = "personal_phone_number", length = 20, nullable = false, unique = true)
    private String personalPhoneNumber;

    @Column(name = "last_education", length = 50, nullable = false)
    private String lastEducation;

    @Column(name = "on_going_education", length = 50)
    private String onGoingEducation;

    @CreationTimestamp
    @Column(name = "join_date", nullable = false, updatable = false,
            columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime joinDate;

    @Column(name = "resign_date")
    private LocalDateTime resignDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_email", nullable = false)
    private User user;
}
 