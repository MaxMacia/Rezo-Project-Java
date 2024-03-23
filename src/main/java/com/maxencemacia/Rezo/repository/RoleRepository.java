package com.maxencemacia.Rezo.repository;

import com.maxencemacia.Rezo.entity.authentication.ERole;
import com.maxencemacia.Rezo.entity.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole roleName);
}
