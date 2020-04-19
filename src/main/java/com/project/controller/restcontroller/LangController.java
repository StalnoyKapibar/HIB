package com.project.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.model.Genre;
import com.project.util.LocaleHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

@RestController
public class LangController {

    @GetMapping("/lang/{lang}")
    public ResponseEntity setChosenLanguage(@PathVariable("lang") String lang, HttpServletRequest request) throws JsonProcessingException {
        request.getSession(false).setAttribute("LANG", lang);
        //TODO: logic for processing chosen language
        return ResponseEntity.status(HttpStatus.OK).body("{'message':'empty'}" /* "some value instead" */);
    }

    @GetMapping("/lang")
    @Autowired
    public ResponseEntity getAllSupportLanguage(LocaleHolder localeHolder) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(localeHolder.getFields());
    }

    @PostMapping("/lang")
    public void setCurrentLangForAdminPanel(@RequestBody String lang, HttpServletResponse response, HttpServletRequest request) {
        request.getSession().setAttribute("LANG", lang);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping(value = "/properties/{lang}")
    public ResponseEntity getPropertyFile(@PathVariable("lang") String lang) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/messages_" + lang + ".properties");
        Properties properties = new Properties();
        properties.load(new InputStreamReader(inputStream, "UTF-8"));
        return ResponseEntity.status(HttpStatus.OK).body(properties);
    }
}
