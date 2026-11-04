package com.example.demo.partime;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartTimeRepository extends JpaRepository<PartTimeEmployee, Integer> {

    Optional<PartTimeEmployee> findByUser_WorkEmail(String workEmail);

}
