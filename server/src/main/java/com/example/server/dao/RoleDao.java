package tst.pp08.dao;

import tst.pp08.model.Role;


import java.util.List;
import java.util.Set;

public interface RoleDao  {
    void add(Role role);
    List<Role> getAllRoles();
    Role getRoleById(int id);

}
