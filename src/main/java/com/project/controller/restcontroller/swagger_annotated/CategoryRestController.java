package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.*;
import com.project.service.CategoryService;
import com.project.service.abstraction.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "REST-API документ для раздела: категории книг.")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryRestController {

    CategoryService categoryService;

    BookService bookService;

    @ApiOperation(value = "Получить list с категориями книг по параметру 'name'"
            , notes = "Этот ендпойнт вернёт list с категориями книг по параметру 'name'"
            , response = Category.class
            , responseContainer = "List"
            , tags = "getPanelCategories")
    @GetMapping("/getpanelcategories/{name}")
    public List<Category> getPanelCategories(@ApiParam(value = "name", required = true)@PathVariable("name") String name) {
        return categoryService.getListCategoriesById(name);
    }

    @ApiOperation(value = "Получить дерево категорий для региональной локали",
            notes = "Этот ендпойнт вернёт list с категориями по параметру 'loc'"
            , response = Category.class
            , responseContainer = "List"
            , tags = "getCategoryTreeByLocale")
    @GetMapping("/gettree/{loc}")
    public List<Category> getCategoryTree(@ApiParam(value = "locale", required = true)@PathVariable("loc") String loc) {
        return categoryService.getCategoryTree(loc);
    }

    @ApiOperation(value = "Получить все книги по категориям по параметру 'path'",
            notes = "Этот ендпойнт вернёт list с BookDTOForCategories по параметру 'path'"
            , response = BookDTOForCategories.class
            , responseContainer = "List"
            , tags = "getBooksByPath")
    @GetMapping("/getbooks")
    public List<BookDTOForCategories> getBooksByPath(@ApiParam(value = "path", required = true)@RequestParam("path") String path) {
        List<BookDTOForCategories> books = new ArrayList<>();
        for (Long id : categoryService.getallChildsIdByPath(path)) {
            books.addAll(bookService.getBooksByCategoryId(id, "en"));
        }
        return books;
    }

    @ApiOperation(value = "Получить число книг по параметру 'path' и параметру 'show'"
            , notes = "Ендпойнт вернёт число книг по параметру 'path' и параметру 'show'"
            , response = Long.class, tags = "getCountBooksByPath")
    @GetMapping("/getcount")
    public Long getCountBooksByPath(@ApiParam(value = "path", required = true)@RequestParam("path") String path, @ApiParam(value = "show", required = true)@RequestParam("show") boolean isShow) {
        long i = 0;
        for (Long id : categoryService.getallChildsIdByPath(path)) {
            i+= bookService.getCountBooksByCategoryId(id, isShow);
        }
        return i;
    }
}