package com.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.util.LocalStringColumns;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.*;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/lang/{lang}")
    public ResponseEntity chosenLanguage(@PathVariable("lang") String lang, HttpServletRequest request) throws JsonProcessingException {
        request.getSession(false).setAttribute("LANG", lang);
        //логика (отправляем locale на service), получаем ответ, отправляем на страницу
        //String response = "dataString";
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(new String("somevalue")));
    }

    @GetMapping("/lang")
    public ResponseEntity getAllSupportLanguage() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ObjectMapper().writeValueAsString(LocalStringColumns.getInstance().getFields()));
    }
}
