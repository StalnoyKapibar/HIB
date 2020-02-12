package tst.pp08.controller;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tst.pp08.model.Role;
import tst.pp08.model.User;
import tst.pp08.service.RoleService;
import tst.pp08.service.UserService;


import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

        return roleService.getAllRoles();
    }


    @PostMapping("/admin/getuser")
    public List<User> getUser() {

        return userService.getUser();
    }

    @GetMapping("/admin/getrolebyid/{id}")
    public User getRoleById(@PathVariable int id) {
        return userService.getById(id);

    }



}
