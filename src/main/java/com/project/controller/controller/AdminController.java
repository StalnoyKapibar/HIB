package com.project.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/panel/**")
    public String getAdminPage() {
        return "admin/admin";
    }

    @GetMapping("/edit")
    public String getEditPage() {
        return "editPageBook";
    }
}
