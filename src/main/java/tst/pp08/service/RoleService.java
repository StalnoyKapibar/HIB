package tst.pp08.service;

import tst.pp08.model.Role;

import java.util.List;

public interface RoleService {
    void add(Role role);
    List<Role> getAllRoles();
    Role getRoleById(int id);


}
