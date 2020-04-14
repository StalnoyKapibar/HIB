package com.project.controller.restcontroller;

import com.project.util.TransliterateUtilImpl;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TransliterateForBookController {

    private TransliterateUtilImpl transliterateForBookImpL;

    @PostMapping("/api/transliteration")
    public String transliteration(@RequestBody String text) throws ParseException {

        return transliterateForBookImpL
                .transliterate(text);
    }
}
