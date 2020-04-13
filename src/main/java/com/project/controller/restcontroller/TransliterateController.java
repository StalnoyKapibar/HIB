package com.project.controller.restcontroller;

import com.project.transliterate.TransliterateImp;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TransliterateController {

    private TransliterateImp transliterationImp;

    @PostMapping("/api/transliteration")
    public String transliteration(@RequestBody String json) throws ParseException {

        return transliterationImp
                .transliterate(new JSONParser(json)
                        .string());
    }
}
