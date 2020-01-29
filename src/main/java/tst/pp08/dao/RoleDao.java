package tst.pp08.dao;

import tst.pp08.model.Role;


import java.util.List;

public interface RoleDao  {
    void add(Role role);
    List<Role> getAllRoles();
    Role getRoleById(int id);
}
