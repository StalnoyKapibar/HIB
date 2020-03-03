package com.project.controller;

import com.project.model.BookDTO;
import com.project.model.WelcomeLocaleDTO;
import com.project.service.BookService;
import com.project.util.VarBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/admin/pageable/{page1}")
    public Page<BookDTO> getWelcomeLocaleDTOByLocale(@PathVariable("page1") int page1) {
        Pageable pageable0 = PageRequest.of(page1, 10, Sort.by(
                Sort.Order.asc("id")));
        Page<BookDTO> page = bookService.findAll(pageable0);
        return page;
    }

    @GetMapping("/getPageBooks")
    public List<BookDTO> getPageBooks() {
        return bookService.getAllBookDTO();
    }

    @PostMapping("/admin/add")
    public void addBook(@RequestBody BookDTO bookDTO) {
        bookService.addBook(bookDTO);
    }

    @GetMapping("/getVarBookDTO")
    @Autowired
    public List<String> getVarBookDTO(VarBookDTO varBookDTO) {
        return varBookDTO.getFields();
    }

    @GetMapping("/admin/del/{x}")
    public void delBook(@PathVariable("x") long x) {
        bookService.deleteBookById(x);
    }

    @PostMapping("/admin/edit")
    public void editBook(@RequestBody BookDTO bookDTO) {
        bookService.updateBook(bookDTO);
    }

    @GetMapping("/admin/get20BookDTO")
    public List<BookDTO> getWelcomeLocaleDTOByLocaleSize20() {
        List<BookDTO> page = bookService.get20BookDTO();
        return page;
    }
}
