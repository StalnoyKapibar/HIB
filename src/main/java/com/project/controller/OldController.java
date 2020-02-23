package com.project.controller;

import com.project.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OldController {

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String printIndex() {
        return "home";
    }
}
