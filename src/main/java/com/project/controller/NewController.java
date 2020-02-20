package com.project.controller;


import com.project.model.Welcome;
import com.project.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




@RestController
public class NewController {

    @Autowired
    private WelcomeService welcomeService;


    @PostMapping("/welcome")
    public Welcome getWelcome() {
        return welcomeService.getWelcome();

    }

    @PostMapping("/welcome/edit")
    public void setWelcome(@RequestBody Welcome welcome) {
        welcomeService.setWelcome(welcome);

    }



}
