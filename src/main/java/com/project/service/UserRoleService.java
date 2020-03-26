package com.project.service;

import com.project.model.UserRole;

public interface UserRoleService {

    UserRole getUserRoleByName(String name);

    void save(UserRole userRole);
}
