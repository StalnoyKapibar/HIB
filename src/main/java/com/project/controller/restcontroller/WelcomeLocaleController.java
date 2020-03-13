package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import com.project.service.BookService;
import com.project.service.WelcomeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class WelcomeLocaleController {

    private BookService bookService;

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
    public void getAllAd() {
        List<BookDTO> list = bookService.getAllBookDTO();
    }
}