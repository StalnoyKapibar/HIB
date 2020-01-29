package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao  {
    void add(Role role);
    List<Role> getAllRoles();
    Role getRoleById(int id);
}
