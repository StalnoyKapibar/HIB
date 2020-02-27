package com.project.controllers;

import com.project.model.LocaleString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/admin")
    public String getEditWelcome() {
        return "admin";
    }
}
