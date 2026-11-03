package com.example.demo.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    Optional<UserRole> findByUser_WorkEmailIgnoreCaseAndRole_NameIgnoreCase(
            String workEmail,
            String roleName);
}
