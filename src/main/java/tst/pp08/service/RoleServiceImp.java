package tst.pp08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tst.pp08.dao.RoleDao;
import tst.pp08.model.Role;


import java.util.List;


@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleDao roleDao;


    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleById(int id) {
        return roleDao.getRoleById(id);
    }

}
