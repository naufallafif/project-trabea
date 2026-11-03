package com.example.demo.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Role> findByNameIgnoreCase(String name);
}
