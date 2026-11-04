package com.example.demo.employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByUser_WorkEmail(String workEmail);
}
