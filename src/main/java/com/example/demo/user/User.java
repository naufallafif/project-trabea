package com.example.demo.user;

import com.example.demo.employee.Employee;
import com.example.demo.partime.PartTimeEmployee;
import com.example.demo.role.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "work_email", length = 100)
    @NotBlank
    @NotNull(message = "Work email is required")
    private String workEmail;

    @Column(name = "password", nullable = false, length = 255)
    @NotNull(message = "Password is required")
    @NotBlank
    private String password;

    // User dapat memiliki banyak role melalui UserRole
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();

    // User dapat direferensikan oleh banyak Employee
    @OneToMany(mappedBy = "user")
    private List<Employee> employees = new ArrayList<>();

    // User dapat direferensikan oleh banyak PartTimeEmployee
    @OneToMany(mappedBy = "user")
    private List<PartTimeEmployee> partTimeEmployees = new ArrayList<>();
}