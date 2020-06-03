package com.project.controller.controller;

import com.project.model.FormLoginErrorMessageDTO;
import com.project.model.RegistrationUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    @GetMapping("/admin/panel/**")
    public String getAdminPage() {
        return "admin/admin";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditPage(@PathVariable String id) {
        ModelAndView view = new ModelAndView("editPageBook");
        return view;
    }
}
