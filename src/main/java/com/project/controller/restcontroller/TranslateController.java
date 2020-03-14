package com.project.controller.restcontroller;

import com.project.translate.HibTranslatorImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@AllArgsConstructor
@RestController
public class TranslateController {

    private HibTranslatorImp translator;

    @PostMapping("/translate")
    public byte[] translateText(@RequestBody String text, HttpServletRequest request) throws UnsupportedEncodingException {
        if (text == null){
            return null;
        }
        String lang = (String) request.getSession().getAttribute("LANG");
        if (lang == null) {
            lang = "ru";
        }
        return translator.translate(text, lang).getBytes("UTF-8");
    }
}
