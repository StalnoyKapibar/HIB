package com.project.controller.restcontroller;

import com.project.translate.HibTranslatorImp;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class TranslateController {

    private HibTranslatorImp translator;

    @PostMapping("/translate")
    public String translateText(@RequestBody String text, HttpServletRequest request) throws UnsupportedEncodingException {
        if (text == null) {
            return null;
        }
        String lang = (String) request.getSession().getAttribute("LANG");
        if (lang == null) {
            lang = "en";
        }
        return translator.translate(text, lang);
    }

    @PostMapping("/translate/list")
    public Map<String, String> translateList(@RequestBody String json) throws ParseException {
        JSONParser jsonParser = new JSONParser(json);
        Map<String, Object> map = jsonParser.parseObject();
        Map<String, String> resp = new HashMap<>();
        String langFrom = (String) map.get("langFrom");
        String text = (String) map.get("text");
        List<String> arrLangTo = (List<String>) map.get("arrLangTo");
        for (String s : arrLangTo) {
            resp.put(s, translator.translate(langFrom, s, text));
        }
        return resp;
    }
}
