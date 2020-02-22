package com.project.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.Welcome;
import com.project.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class WelcomeLocaleController {

    @Autowired
    private WelcomeService welcomeService;


    @PostMapping("/welcome")
    public ResponseEntity<String> getWelcome(@RequestBody String locale){
        String loc = welcomeService.getWelcome(locale);

        HttpHeaders headers = new HttpHeaders();
        headers.add("locale", loc);

        return new ResponseEntity<String>(headers, HttpStatus.OK);


    }

    @PostMapping("/welcome/edit")
    public void setWelcome(@RequestBody Welcome welcome) {
        welcomeService.setWelcome(welcome);

    }


}
