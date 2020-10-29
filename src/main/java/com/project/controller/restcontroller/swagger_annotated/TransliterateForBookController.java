package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.Address;
import com.project.util.TransliterateUtilImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = "REST-API документ, описывающий взаимодействие с сервисом: транслитерации")
@AllArgsConstructor
@RestController
public class TransliterateForBookController {

    private TransliterateUtilImpl transliterateForBookImpL;

    @ApiOperation(value = "Получить тект в транслите"
            , notes = "Ендпойнт возвращает текст в транслите"
            , response = String.class
            , tags = "getTextTranslit")
    @PostMapping("/api/transliteration")
    public String transliteration(@ApiParam(value = "text", required = true)
                                  @RequestBody String text) throws ParseException {

        return transliterateForBookImpL
                .transliterate(text);
    }
}
