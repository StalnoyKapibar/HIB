package com.project.service;

import com.project.dao.UserRoleDao;
import com.project.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleDao userRoleDao;

    @Override
    public UserRole getUserRoleByName(String name) {
        UserRole userRole = userRoleDao.findUserRoleByRoleName(name);
        if (userRole == null) {
            return new UserRole(0l, "UNDEF");
        } else {
            return userRole;
        }
    }

    @Override
    public void save(UserRole userRole) {
        userRoleDao.save(userRole);
    }
}
