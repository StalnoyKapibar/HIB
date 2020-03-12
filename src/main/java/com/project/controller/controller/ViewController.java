package com.project.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String getHomePage(HttpServletRequest request) {
        HttpSession session;
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "ru");
        }
        return "home";
    }


    @GetMapping("/page")
    public String getPage(HttpServletRequest request) {
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "ru");
        }
        return "page";
    }
}
