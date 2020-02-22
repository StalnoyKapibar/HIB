package com.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.util.LocaleHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@RestController
public class UserController {

    @GetMapping("/lang/{lang}")
    public ResponseEntity chosenLanguage(@PathVariable("lang") String lang, HttpServletRequest request) throws JsonProcessingException {
        request.getSession(false).setAttribute("LANG", lang);
        //logic to make answer
        return ResponseEntity.status(HttpStatus.OK).body( null /* "some value instead" */);
    }

    @GetMapping("/lang")
    @Autowired
    public ResponseEntity getAllSupportLanguage(LocaleHolder localeHolder) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(localeHolder.getFields());
    }
}
