package com.example.server.dao;




import com.example.server.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao  {
    void add(Role role);
    List<Role> getAllRoles();
    Role getRoleById(int id);

}
