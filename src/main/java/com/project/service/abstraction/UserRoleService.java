package com.project.service.abstraction;

import com.project.model.Role;

public interface UserRoleService {

    Role getUserRoleByName(String name);

    void save(Role role);
}
