package com.project.service;

import com.project.dao.abstraction.UserRoleDao;
import com.project.model.Role;
import com.project.service.abstraction.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleDao userRoleDao;

    @Override
    public Role getUserRoleByName(String name) {
        Role role = userRoleDao.findUserRoleByRoleName(name);
        if (role == null) {
            return new Role(0l, "UNDEF");
        } else {
            return role;
        }
    }

    @Override
    public void save(Role role) {
        userRoleDao.save(role);
    }
}
