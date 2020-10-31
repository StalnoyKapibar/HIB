package com.project.controller.restcontroller.swagger_annotated;

import com.project.dao.CategoryDAO;
import com.project.model.BookNewDTO;
import com.project.model.BookSearchPageDTO;
import com.project.search.BookSearch;
import com.project.service.abstraction.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Api(value = "REST-API документ, описывающий взаимодействие с сервисом: поиска книг")
@RestController
public class SearchController {

    private final BookSearch bookSearch;
    private final BookService bookService;
    private final CategoryDAO categoryDAO;

    @Autowired
    public SearchController(BookSearch bookSearch, BookService bookService, CategoryDAO categoryDAO) {
        this.bookSearch = bookSearch;
        this.bookService = bookService;
        this.categoryDAO = categoryDAO;
    }

    @PostMapping("/api/admin/searchResult")
    public List<BookNewDTO> BooksSearchAdmin(@RequestParam(value = "request") String req, @RequestParam(value = "Show") boolean isShow) {
        return bookSearch.searchAdmin(req, isShow);
    }

    @ApiOperation(value = "Получить все книги по Id категории",
            notes = "Возвращает List обьектов BookNewDTO по id категории",
            response = BookNewDTO.class, responseContainer = "List",
            tags = "GetBooksByCategory")
    @GetMapping("/search/byCategory")
    public List<BookNewDTO> BooksByCategory(@ApiParam(value = "Id категории", required = true) @RequestParam(value = "categoryId") Long categoryId) {
        return bookSearch.searchByCategory(categoryId);
    }

    @ApiOperation(value = "Получить книги определенных категорий по параметрам",
            notes = "Возвращает объект BookSearchPageDTO",
            response = BookSearchPageDTO.class,
            tags = "GetBooksSearchByParameters")
    @GetMapping("/searchAdvanced")
    public BookSearchPageDTO BooksSearchByParameters(
            @ApiParam(value = "Значение строки поиска", required = true)
                @RequestParam(value = "request") String request,
            @ApiParam(value = "Значение поиска по наименованию и/или автору", required = true)
                @RequestParam(value = "searchBy") String searchBy,
            @ApiParam(value = "List ID категорий", required = true)
                @RequestParam List<Long> categories,
            @ApiParam(value = "Нижняя граница цены", required = true)
                @RequestParam(value = "priceFrom") Long priceFrom,
            @ApiParam(value = "Верхняя граница цены", required = true)
                @RequestParam(value = "priceTo") Long priceTo,
            @ApiParam(value = "Год редакции от", required = true)
                @RequestParam(value = "yearOfEditionFrom") Long yearOfEditionFrom,
            @ApiParam(value = "Год редакции до", required = true)
                @RequestParam(value = "yearOfEditionTo") Long yearOfEditionTo,
            @ApiParam(value = "Нижняя граница кол-ва страниц", required = true)
                @RequestParam(value = "pagesFrom") Long pagesFrom,
            @ApiParam(value = "Верхняя граница количества страниц", required = true)
                @RequestParam(value = "pagesTo") Long pagesTo,
            @ApiParam(value = "Номер страницы", required = true)
                @RequestParam(value = "page") int page,
            @ApiParam(value = "Количество книг на странице", required = true)
                @RequestParam(value = "size") int size,
            @ApiParam(value = "Статус наличия книги", required = true)
                @RequestParam(value = "show") boolean isShow) {
        Pageable pageable = PageRequest.of(page, size);
        BookSearchPageDTO books = bookSearch.searchByParameters(request.toLowerCase(), priceFrom, priceTo, String.valueOf(yearOfEditionFrom), String.valueOf(yearOfEditionTo),
                pagesFrom, pagesTo, searchBy, categories, pageable, isShow);
        return books;
    }

    @ApiOperation(value = "Получить книги всех котегорий по параметрам",
                  notes = "Возвращает объект BookSearchPageDTO",
                  response = BookSearchPageDTO.class,
                  tags = "GetBooksSearchByParametersAllCategories")
    @GetMapping("/searchAdvancedAllCategories")
    public BookSearchPageDTO BooksSearchByParameters(@ApiParam(value = "Значение строки поиска", required = true)
                                                         @RequestParam(value = "request") String request,
                                                     @ApiParam(value = "Значение поиска по наименованию и/или автору", required = true)
                                                        @RequestParam(value = "searchBy") String searchBy,
                                                     @ApiParam(value = "Нижняя граница цены", required = true)
                                                         @RequestParam(value = "priceFrom") Long priceFrom,
                                                     @ApiParam(value = "Верхняя граница цены", required = true)
                                                         @RequestParam(value = "priceTo") Long priceTo,
                                                     @ApiParam(value = "Год редакции от", required = true)
                                                         @RequestParam(value = "yearOfEditionFrom") Long yearOfEditionFrom,
                                                     @ApiParam(value = "Год редакции до", required = true)
                                                         @RequestParam(value = "yearOfEditionTo") Long yearOfEditionTo,
                                                     @ApiParam(value = "Нижняя граница кол-ва страниц", required = true)
                                                         @RequestParam(value = "pagesFrom") Long pagesFrom,
                                                     @ApiParam(value = "Верхняя граница количества страниц", required = true)
                                                         @RequestParam(value = "pagesTo") Long pagesTo,
                                                     @ApiParam(value = "Номер страницы", required = true)
                                                         @RequestParam(value = "page") int page,
                                                     @ApiParam(value = "Количество книг на странице", required = true)
                                                         @RequestParam(value = "size") int size,
                                                     @ApiParam(value = "Статус наличия книги", required = true)
                                                         @RequestParam(value = "show") boolean isShow) {
        Pageable pageable = PageRequest.of(page, size);
        List<Long> categories = categoryDAO.getIdsCategories();
        BookSearchPageDTO books = bookSearch.searchByParameters(request.toLowerCase(), priceFrom, priceTo, String.valueOf(yearOfEditionFrom), String.valueOf(yearOfEditionTo),
                pagesFrom, pagesTo, searchBy, categories, pageable, isShow);
        return books;
    }

    @Deprecated
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
