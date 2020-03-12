package com.project.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.util.LocaleHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@RestController
public class UserController {

    @GetMapping("/lang/{lang}")
    public ResponseEntity setChosenLanguage(@PathVariable("lang") String lang, HttpServletRequest request) throws JsonProcessingException {
        request.getSession(false).setAttribute("LANG", lang);
        //TODO: logic for processing chosen language
        return ResponseEntity.status(HttpStatus.OK).body(null /* "some value instead" */);
    }

    @GetMapping("/lang")
    @Autowired
    public ResponseEntity getAllSupportLanguage(LocaleHolder localeHolder) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(localeHolder.getFields());
    }

    @GetMapping("/properties/{lang}")
    public ResponseEntity getPropertyFile(@PathVariable("lang") String lang) throws IOException {
        String path = this.getClass().getClassLoader().getResource("static/messages_" + lang + ".properties").getPath();
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
        return ResponseEntity.status(HttpStatus.OK).body(properties);
    }
}
