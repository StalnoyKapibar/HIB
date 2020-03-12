package com.project.controller.controller;

import com.project.model.BookDTO;
import com.project.model.LocaleString;
import com.project.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    BookService bookService;
    @GetMapping("/admin")
    public String getAdminPage() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName(new LocaleString("asdfasdf","asdf","asdf","","","",""));
        bookDTO.setAuthor(new LocaleString("asdfasdf","asdf","asdf","","","",""));
        bookService.addBook(bookDTO);
        return "admin";
    }
}
