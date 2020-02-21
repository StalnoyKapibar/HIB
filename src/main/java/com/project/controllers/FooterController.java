package com.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



    @Controller
    public class FooterController {

        @GetMapping("/footer")
        public String getInternationalPage() {
            return "footer";
        }
    }

