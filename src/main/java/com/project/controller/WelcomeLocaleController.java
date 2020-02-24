package com.project.controller;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import com.project.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WelcomeLocaleController {

    @Autowired
    private WelcomeService welcomeService;

    @PostMapping("/welcome")
    public ResponseEntity getWelcomeLocaleDTOByLocale(@RequestBody String locale) {
        WelcomeLocaleDTO welcomeLocaleDTO = welcomeService.getWelcomeLocaleDTOByLocale(locale);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/welcome/edit")
    public void editWelcome(@RequestBody Welcome welcome) {
        welcomeService.editWelcome(welcome);
    }
}