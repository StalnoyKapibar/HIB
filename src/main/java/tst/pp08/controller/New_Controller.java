package tst.pp08.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tst.pp08.model.Role;
import tst.pp08.model.User;
import tst.pp08.service.RoleService;
import tst.pp08.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
public class New_Controller {
    private List<Role> allRoles0;


    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/admin/add")
    // @ResponseBody
    public String addUser(@RequestBody User user, String[] id) {
        // allRoles0 = roleService.getAllRoles();
        List<Role> allRoles = new ArrayList<>(allRoles0);
        List<Role> existAllRoles = new ArrayList<>();
        for (Role rolll : allRoles) {
            int qqq = 0;

            for (int i = 0; i < id.length; i++) {
                qqq = Integer.parseInt(id[i]);
                int dig = rolll.getId();
                if (dig == qqq) {
                    existAllRoles.add(rolll);
                }
            }
        }


        // Stream.of(roleId).map(Role::new).collect(Collectors.toList());
        allRoles.retainAll(existAllRoles);
        user.setRole(new HashSet<>(allRoles));
        userService.add(user);
        return "redirect:/admin";
    }


    @RequestMapping("/admin/alcoves")
    public List<Role> sendAllRoles() {
        allRoles0 = roleService.getAllRoles();
        return allRoles0;
    }


}
