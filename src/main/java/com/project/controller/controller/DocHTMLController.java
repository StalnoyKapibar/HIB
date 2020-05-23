package com.project.controller.controller;

import com.project.service.abstraction.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocHTMLController {

    private final BookService service;

    public DocHTMLController(BookService service) {
        this.service = service;
    }

    @GetMapping("/mail/document")
    public String getHTMLDocument(long id, Model model){
        model.addAttribute("book", service.getBookById(id) );
        return "test-mail";
    }


}
