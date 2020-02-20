package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OldController {

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String printIndex() {

        return "home";
    }
}
