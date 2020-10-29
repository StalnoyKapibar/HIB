package com.project.controller.restcontroller.swagger_annotated;

import com.project.dao.CategoryDAO;
import com.project.model.BookNewDTO;
import com.project.model.BookSearchPageDTO;
import com.project.search.BookSearch;
import com.project.service.abstraction.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "REST-API документ, описывающий взаимодействие с сервисом: поиска книг")
@RestController
public class SearchRestController {

    private final BookSearch bookSearch;
    private final BookService bookService;
    private final CategoryDAO categoryDAO;

    @Autowired
    public SearchRestController(BookSearch bookSearch, BookService bookService, CategoryDAO categoryDAO) {
        this.bookSearch = bookSearch;
        this.bookService = bookService;
        this.categoryDAO = categoryDAO;
    }


    @PostMapping("/api/admin/searchResult")
    public List<BookNewDTO> BooksSearchAdmin(@RequestParam(value = "request") String req, @RequestParam(value = "Show") boolean isShow) {
        return bookSearch.searchAdmin(req, isShow);
    }

    @GetMapping("/search/byCategory")
    public List<BookNewDTO> BooksByCategory(@RequestParam(value = "categoryId") Long categoryId) {
        return bookSearch.searchByCategory(categoryId);
    }

    @GetMapping("/searchAdvanced")
    public BookSearchPageDTO BooksSearchByParameters(@RequestParam(value = "request") String request, @RequestParam(value = "searchBy") String searchBy,
                                                     @RequestParam List<Long> categories, @RequestParam(value = "priceFrom") Long priceFrom,
                                                     @RequestParam(value = "priceTo") Long priceTo, @RequestParam(value = "yearOfEditionFrom") Long yearOfEditionFrom,
                                                     @RequestParam(value = "yearOfEditionTo") Long yearOfEditionTo, @RequestParam(value = "pagesFrom") Long pagesFrom,
                                                     @RequestParam(value = "pagesTo") Long pagesTo, @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size, @RequestParam(value = "show") boolean isShow) {
        Pageable pageable = PageRequest.of(page, size);
        BookSearchPageDTO books = bookSearch.searchByParameters(request.toLowerCase(), priceFrom, priceTo, String.valueOf(yearOfEditionFrom), String.valueOf(yearOfEditionTo),
                pagesFrom, pagesTo, searchBy, categories, pageable, isShow);
        return books;
    }

    @GetMapping("/searchAdvancedAllCategories")
    public BookSearchPageDTO BooksSearchByParameters(@RequestParam(value = "request") String request, @RequestParam(value = "searchBy") String searchBy,
                                                     @RequestParam(value = "priceFrom") Long priceFrom,
                                                     @RequestParam(value = "priceTo") Long priceTo, @RequestParam(value = "yearOfEditionFrom") Long yearOfEditionFrom,
                                                     @RequestParam(value = "yearOfEditionTo") Long yearOfEditionTo, @RequestParam(value = "pagesFrom") Long pagesFrom,
                                                     @RequestParam(value = "pagesTo") Long pagesTo, @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size, @RequestParam(value = "show") boolean isShow) {
        Pageable pageable = PageRequest.of(page, size);
        List<Long> categories = categoryDAO.getIdsCategories();
        BookSearchPageDTO books = bookSearch.searchByParameters(request.toLowerCase(), priceFrom, priceTo, String.valueOf(yearOfEditionFrom), String.valueOf(yearOfEditionTo),
                pagesFrom, pagesTo, searchBy, categories, pageable, isShow);
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
