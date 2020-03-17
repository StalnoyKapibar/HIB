package com.project.controller;

import com.project.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/admin")
    public String getAdminPage() {
        storageService.deleteAll();
        storageService.createTmpFolderForImages();
        return "admin";
    }

    @GetMapping("/edit")
    public String getEditPage() {
        return "editPageBook";
    }
}
