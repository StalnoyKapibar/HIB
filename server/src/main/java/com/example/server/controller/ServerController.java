package com.example.server.controller;

import com.example.server.dao.RoleDao;
import com.example.server.dao.UserDao;
import com.example.server.model.Role;
import com.example.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
public class ServerController {


    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;


    @PostMapping("/server/add")

    public void addUser(@RequestBody User user) {

        userDao.add(user);

    }


    @GetMapping("/server/getuser")

    public List<User> getUser() {

        return userDao.getUser();


    }


    @PostMapping("/server/findbyusername")

    public User findByUsername(@RequestBody String username) {

        return userDao.findByUsername(username);


    }

    @PostMapping("/server/update")

    public void update(@RequestBody User user) {

        userDao.update(user);


    }


    @PostMapping("/server/del")

    public void del(@RequestBody int id) {

        userDao.delete(id);


    }

    @PostMapping("/server/getbyid")

    public User getById(@RequestBody int id) {

        return userDao.getUserById(id);


    }


    @PostMapping("/server/addrole")

    public void addRole(@RequestBody Role role) {

        roleDao.add(role);

    }


    @GetMapping("/server/allroles")

    public List<Role> allRoles() {

        return roleDao.getAllRoles();

    }


    @PostMapping("/server/getrolebyid")

    public Role getRoleById(@RequestBody int id) {

        return roleDao.getRoleById(id);

    }


}
