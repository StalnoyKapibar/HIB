package com.project.service;

import com.project.model.Role;

public interface UserRoleService {

    Role getUserRoleByName(String name);

    void save(Role role);
}
