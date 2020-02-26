package com.project.controller;

import com.project.model.LocaleString;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import com.project.service.WelcomeService;
import com.project.util.LocaleHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WelcomeLocaleController {

    @Autowired
    private WelcomeService welcomeService;

    @GetMapping("/welcome/{locale}")
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(@PathVariable("locale") String locale) {
        return welcomeService.getWelcomeLocaleDTOByLocale(locale);
    }

    @PostMapping("/welcome/edit")
    public void editWelcome(@RequestBody Welcome welcome) {
        welcome.setId(1);
        LocaleString localeString= welcome.getBody();
        localeString.setId((long)1);
        welcome.setBody(localeString);
        welcomeService.editWelcome(welcome);
    }
}