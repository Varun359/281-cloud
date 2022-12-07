package com.cmpe281.team12.ccrs.repository;

import com.cmpe281.team12.ccrs.model.ERole;
import com.cmpe281.team12.ccrs.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
