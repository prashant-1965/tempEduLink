package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query("select r from Role r where r.roleName = :roleName")
    Optional<Role> findRoleByName(@Param("roleName") String roleName);
}
