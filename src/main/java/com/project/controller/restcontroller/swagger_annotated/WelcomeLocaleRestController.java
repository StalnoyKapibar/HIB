package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.*;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.WelcomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "REST-API документ, описывающий взаимодействие с сервисом: bookService и welcomeService")
@AllArgsConstructor
@RestController
public class WelcomeLocaleRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeLocaleRestController.class.getName());
    private BookService bookService;
    private WelcomeService welcomeService;


    @ApiOperation(value = "получить WelcomeLocaleDTO"
            , notes = "Эндпоинт получает параметр locale типа String, в адресе запроса. " +
            "Эндпоинт возрщает WelcomeLocaleDTO"
            , response = WelcomeLocaleDTO.class
            , tags = "getWelcomeLocaleDTOByLocale")
    @GetMapping("/api/welcome/locale/{locale}")
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(@PathVariable("locale") String locale) {
        return welcomeService.getWelcomeLocaleDTOByLocale(locale);
    }

    @ApiOperation(value = "добавить Welcome"
            , notes = "Эндпоинт получает параметр welcome типа Welcome. " +
            "Эндпоинт добавляет в БД Welcome"
            , response = Void.class
            , tags = "editWelcome")
    @PostMapping("/api/admin/welcome/edit")
    public void editWelcome(@RequestBody Welcome welcome) {
        LOGGER.debug("POST request 'welcome/edit' with {}", welcome);
        welcomeService.editWelcome(welcome);
    }

    @ApiOperation(value = "получить Welcome"
            , notes = "Эндпоинт получает параметр id типа Long, в адресе запроса. " +
            "Эндпоинт возращает Welcome"
            , response = Welcome.class
            , tags = "getWelcome")
    @GetMapping("/api/welcome/{id}")
    public Welcome getWelcome(@PathVariable Long id) {
        return welcomeService.getById(id);
    }

    @ApiOperation(value = "Получить все книги"
            , notes = "Эндпоинт возвращает список с книгами"
            , response = Book.class
            , responseContainer = "List"
            , tags = "getAllAd")
    @GetMapping("/get")
    public List<Book> getAllAd() {
        return bookService.getAllBookDTO();
    }
}