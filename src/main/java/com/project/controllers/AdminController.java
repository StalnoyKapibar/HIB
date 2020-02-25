package com.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.LocaleString;
import com.project.model.WelcomeLocaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@RestController
public class AdminController {

    @GetMapping("/admin/allLocales")

    public ResponseEntity getAllLocales(){
        String asd;
        List<Field> fields =  Arrays.asList(LocaleString.class.getDeclaredFields());
        String[] strings = new String[fields.size()-1];;
        for (int i = 1; i < fields.size(); i++) {
            strings[i-1] = fields.get(i).getName();
        }
        return ResponseEntity.status(HttpStatus.OK).body(strings);
    }
}
