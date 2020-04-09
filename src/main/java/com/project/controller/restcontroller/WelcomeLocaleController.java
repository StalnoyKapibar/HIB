package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import com.project.service.BookService;
import com.project.service.WelcomeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class WelcomeLocaleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeLocaleController.class.getName());
    private BookService bookService;
    private WelcomeService welcomeService;

    @GetMapping("/api/welcome/locale/{locale}")
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(@PathVariable("locale") String locale) {
        return welcomeService.getWelcomeLocaleDTOByLocale(locale);
    }

    @PostMapping("/api/admin/welcome/edit")
    public void editWelcome(@RequestBody Welcome welcome) {
        LOGGER.debug("POST request 'welcome/edit' with {}", welcome);
        welcomeService.editWelcome(welcome);
    }

    @GetMapping("/api/welcome/{id}")
    public Welcome getWelcome(@PathVariable Long id) {
        return welcomeService.getById(id);
    }

    @GetMapping("/get")
    public List<BookDTO> getAllAd() {
        return bookService.getAllBookDTO();
    }
}