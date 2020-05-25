package com.project.controller.restcontroller;

import com.project.dao.CategoryDAO;
import com.project.model.BookNewDTO;
import com.project.search.BookSearch;
import com.project.service.abstraction.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController {

    private final BookSearch bookSearch;
    private final BookService bookService;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    public SearchController(BookSearch bookSearch, BookService bookService) {
        this.bookSearch = bookSearch;
        this.bookService = bookService;
    }

    @PostMapping("/api/admin/searchResult")
    public List<BookNewDTO> search(@RequestParam(value = "request") String req, @RequestParam(value = "Show") boolean isShow) {
        return bookSearch.search(req, isShow);
    }

    @GetMapping("/search/byCategory")
    public List<BookNewDTO> search(@RequestParam(value = "categoryId") Long categoryId) {
        return bookSearch.search(categoryId);
    }

    @GetMapping("/searchResult")
    public List<BookNewDTO> search(@RequestParam(value = "request") String req) {
        return bookSearch.search(req);
    }

    @GetMapping("/searchAdvanced")
    public List<BookNewDTO> advancedSearch(@RequestParam(value = "request") String request, @RequestParam(value = "searchBy") String searchBy,
                                           @RequestParam List<String> categories, @RequestParam(value = "priceFrom") Long priceFrom,
                                           @RequestParam(value = "priceTo") Long priceTo, @RequestParam(value = "yearOfEditionFrom") Long yearOfEditionFrom,
                                           @RequestParam(value = "yearOfEditionTo") Long yearOfEditionTo, @RequestParam(value = "pagesFrom") Long pagesFrom,
                                           @RequestParam(value = "pagesTo") Long pagesTo) {
        List<BookNewDTO> books = bookSearch.search(request, priceFrom, priceTo, String.valueOf(yearOfEditionFrom), String.valueOf(yearOfEditionTo),
                pagesFrom, pagesTo, searchBy, categories);
        return books;
    }

    @GetMapping("/api/booksSearchPage")
    public List<BookNewDTO> getAllBooksSearchPage() {
        List<BookNewDTO> books = bookService.getAllBooksSearchPage();
        return books;
    }

    @GetMapping("api/search/{categoryId}")
    public List<BookNewDTO> getBooksByCategory(@PathVariable("categoryId") long categoryId) {
        List ids = categoryDAO.getAllChildsIdByParentId(categoryId);
        List<BookNewDTO> books = new ArrayList<>();
        for (Object id : ids) {
            Long idL = Long.parseLong(String.valueOf(id));
            books.addAll(bookService.getBooksByCategoryId(idL));
        }
        return books;
    }
}
