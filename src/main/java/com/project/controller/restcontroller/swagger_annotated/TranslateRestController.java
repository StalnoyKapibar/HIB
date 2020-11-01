package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.Address;
import com.project.model.TranslateListDTO;
import com.project.translate.HibTranslatorImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "REST-API документ, описывающий взаимодействие с сервисом перевода: HibTranslator, использующим API 'translate.yandex.net'")
@AllArgsConstructor
@RestController
public class TranslateRestController {

    private HibTranslatorImp translator;

    @ApiOperation(value = "Перевести текст"
            , notes = "Ендпойнт получает два параметра типа string: текст из тела запроса" +
            " и параметр с именем \"LANG\" и значением = региональной локали из HttpSession. " +
            "Ендпойнт возвращает стоку перевода."
            , response = String.class
            , tags = "translateText")
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

    @ApiOperation(value = "Перевести текст на несколько языков"
            , notes = "Ендпойнт получает объект TranslateListDTO. " +
            "Ендпойнт возвращает ассоциативный массив с ключами-локалями и значениями-переводами."
            , response = Map.class
            , tags = "translateList")
    @PostMapping("/translate/list")
    public Map<String, String> translateList(@RequestBody TranslateListDTO manyLocales) throws ParseException {
//    public Map<String, String> translateList(@RequestBody String json) throws ParseException {
//        JSONParser jsonParser = new JSONParser(json);
//        Map<String, Object> map = jsonParser.parseObject();
//        Map<String, String> resp = new HashMap<>();
//        String langFrom = (String) map.get("langFrom");
//        String text = (String) map.get("text");
//        List<String> arrLangTo = (List<String>) map.get("arrLangTo");
//        for (String s : manyLocales.getArrLangTo()) {
//            resp.put(s, translator.translate(langFrom, s, text));
//        }

        Map<String, String> resp = new HashMap<>();
        for (String s : manyLocales.getLangTo()) {
            resp.put(s, translator.translate(manyLocales.getLangFrom(), s, manyLocales.getText()));
        }
        return resp;
    }
}
