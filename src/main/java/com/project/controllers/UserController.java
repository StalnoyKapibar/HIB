package com.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/lang/{lang}")
    public ResponseEntity chooseLanguage(@PathVariable("lang") String lang) throws JsonProcessingException {

        //логика (отправляем locale на service), получаем ответ, отправляем на страницу
        String response = "dataString";
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(response));
    }
}
