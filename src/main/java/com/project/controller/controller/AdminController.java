package com.project.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/panel/**")
    public String getAdminPage() {
        return "admin/admin-page";
    }

    @GetMapping("/admin/edit/{id}")
    public String getEditPage() {
        return "editPageBook";
    }

    @GetMapping("/admin/addHibFile")
    public String getHibFileAddPage(){
        return "admin/addHibFile";
    }

    @GetMapping("/admin/addBookPage")
    public String getAddBookPage(){
        return "admin/addBook";
    }
}
