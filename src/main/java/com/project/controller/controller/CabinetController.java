package com.project.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CabinetController {

    @GetMapping("/cabinet")
    public String getCabinetPage() {
        return "cabinet";
    }
}
