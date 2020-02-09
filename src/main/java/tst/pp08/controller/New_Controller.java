package tst.pp08.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tst.pp08.model.Role;
import tst.pp08.model.User;
import tst.pp08.service.RoleService;
import tst.pp08.service.UserService;


import java.util.List;

@RestController
public class New_Controller {



    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;



    @PostMapping("/admin/add")
    public String addUser(@RequestBody User user) {

            userService.add(user);


        return "redirect:/admin";
    }

    @PostMapping("/admin/alcoves")
    public List<Role> sendAllRoles() {

        return HelloController.allRoles0;
    }


}
