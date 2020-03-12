package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import com.project.service.BookService;
import com.project.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WelcomeLocaleController {

    @Autowired
    private BookService bookService;

    @Autowired
    private WelcomeService welcomeService;

    @GetMapping("/welcome/{locale}")
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(@PathVariable("locale") String locale) {
        return welcomeService.getWelcomeLocaleDTOByLocale(locale);
    }

    @PostMapping("/welcome/edit")
    public void editWelcome(@RequestBody Welcome welcome) {
        welcomeService.editWelcome(welcome);
    }

    @GetMapping("/get")
    public void getallad(){
       List<BookDTO> list = bookService.getAllBookDTO();
    }
}