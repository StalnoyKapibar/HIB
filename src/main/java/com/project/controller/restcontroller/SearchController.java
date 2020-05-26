package com.project.controller.restcontroller;

import com.project.dao.CategoryDAO;
import com.project.model.BookNewDTO;
import com.project.model.BookPageDto;
import com.project.model.BookSearchPageDTO;
import com.project.search.BookSearch;
import com.project.search.BookSearchPageable;
import com.project.service.abstraction.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    private final BookSearch bookSearch;
    private final BookSearchPageable bookSearchPageable;
    private final BookService bookService;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    public SearchController(BookSearch bookSearch, BookSearchPageable bookSearchPageable, BookService bookService) {
        this.bookSearch = bookSearch;
        this.bookSearchPageable = bookSearchPageable;
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
    public BookSearchPageDTO advancedSearch(@RequestParam(value = "request") String request, @RequestParam(value = "searchBy") String searchBy,
                                           @RequestParam List<String> categories, @RequestParam(value = "priceFrom") Long priceFrom,
                                           @RequestParam(value = "priceTo") Long priceTo, @RequestParam(value = "yearOfEditionFrom") Long yearOfEditionFrom,
                                           @RequestParam(value = "yearOfEditionTo") Long yearOfEditionTo, @RequestParam(value = "pagesFrom") Long pagesFrom,
                                           @RequestParam(value = "pagesTo") Long pagesTo, @RequestParam(value = "limit") int limit,
                                           @RequestParam(value = "start") int start) {
        Pageable pageable = PageRequest.of(start, limit, Sort.by(Sort.Order.asc("id")));
        BookSearchPageDTO bookSearchPageDTO = bookSearchPageable.search(request, priceFrom, priceTo, String.valueOf(yearOfEditionFrom), String.valueOf(yearOfEditionTo),
                pagesFrom, pagesTo, searchBy, categories, pageable);
        return bookSearchPageDTO;
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

    @GetMapping(value = "/api/book", params = {"limit", "start"})
    public BookPageDto getBookDtoByLimitAndAmountAndStart(@RequestParam Map<String, String> params) {
        Pageable pageable = PageRequest.of(Integer.parseInt(params.get("start")),
                Integer.parseInt(params.get("limit")), Sort.by(Sort.Order.asc("id")));
        return bookService.getBookPageByPageable(pageable);
    }
}
