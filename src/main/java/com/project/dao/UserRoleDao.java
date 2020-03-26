package com.project.dao;

import com.project.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Long> {
    UserRole findUserRoleByRoleName(String roleName);
    List<UserRole> findByRoleName(String roleName);
}
