package tst.pp08.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tst.pp08.model.Role;
import tst.pp08.model.User;
import tst.pp08.service.RoleService;
import tst.pp08.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<User> messages = userService.getUser();
        model.addAttribute("messages", messages);
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String printLogin() {

        return "login";
    }

 /*   @GetMapping("/")
    public String getRegistrationPage(Model model) {
		model.addAttribute("roleList", roleService.getAllRoles());
		return "registration";
    }*/




    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String printUser(ModelMap model, Authentication authentication) {
        String name = authentication.getName();
        User user = userService.findByUsername(name);
        model.addAttribute("messages", user);
        model.addAttribute("role", user.getRole().iterator().next().getRole());
        return "User";
    }


    @RequestMapping(value = "admin/add", method = RequestMethod.GET)
    public String printAdd(Model model) {
        //  model.addAttribute("message", session.getAttribute("message"));
        model.addAttribute("roleList", roleService.getAllRoles());

        return "registration";
    }


    @PostMapping("admin/add")
    public String printAddPost(User user, String roleId) {

        Role role = roleService.getRoleById(Integer.parseInt(roleId));
        user.setRole(Collections.singleton(role));
        if (userService.add(user)) {
            //      httpSession.setAttribute("message", "ok");
            //  modelAndView.setViewName("ok");
            //  model.addAttribute("message", "ok");
        } else {
            //   httpSession.setAttribute("message", "логин занят");
            //     modelAndView.setViewName("error");
            //  model.addAttribute("message", "login stop");
        }


        return "redirect:/admin";

    }

/*
    @PostMapping("admin/role")
    public String addRole(@RequestParam String roleName) {
        roleService.add(new Role(roleName));
        return "redirect:/admin/add";
    } */


    @RequestMapping(value = "admin/role", method = RequestMethod.POST)
    public String printRole(String roleName) {
        roleService.add(new Role(roleName));
        return "redirect:/admin/add";

    }


    @RequestMapping(value = "admin/edit", method = RequestMethod.GET)
    public String printEdit(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.getAllRoles());
        return "edit";

    }

    @RequestMapping(value = "admin/edit", method = RequestMethod.POST)
    public String printEditPost(User user, String roleId) {
        Role role = roleService.getRoleById(Integer.parseInt(roleId));
        user.setRole(Collections.singleton(role));
        userService.update(user);
        return "redirect:/admin";

    }

    @RequestMapping(value = "admin/del", method = RequestMethod.GET)
    public String printDel(User user) {
        userService.delete(user.getId());
        return "redirect:/admin";

    }


}