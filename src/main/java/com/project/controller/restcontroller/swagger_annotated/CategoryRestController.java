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

@Api(tags = "This is the REST-API documentation for book categories.")
@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryRestController {

    CategoryService categoryService;

    BookService bookService;

    @ApiOperation(value = "Get list categories for name:", response = Iterable.class, tags = "getPanelCategories")
    @GetMapping("/getpanelcategories/{name}")
    public List<Category> getPanelCategories(@ApiParam(value = "name", required = true)@PathVariable("name") String name) {
        return categoryService.getListCategoriesById(name);
    }

    @ApiOperation(value = "Get category tree by locale", response = Iterable.class, tags = "getCategoryTreeByLocale")
    @GetMapping("/gettree/{loc}")
    public List getCategoryTree(@ApiParam(value = "locale", required = true)@PathVariable("loc") String loc) {
        return categoryService.getCategoryTree(loc);
    }

    @ApiOperation(value = "Get list books by path", response = Iterable.class, tags = "getBooksByPath")
    @GetMapping("/getbooks")
    public List<BookDTOForCategories> getBooksByPath(@ApiParam(value = "path", required = true)@RequestParam("path") String path) {
        List<BookDTOForCategories> books = new ArrayList<>();
        for (Long id : categoryService.getallChildsIdByPath(path)) {
            books.addAll(bookService.getBooksByCategoryId(id, "en"));
        }
        return books;
    }

    @ApiOperation(value = "Get count books", response = Long.class, tags = "getCountBooksByPath")
    @GetMapping("/getcount")
    public Long getCountBooksByPath(@ApiParam(value = "path", required = true)@RequestParam("path") String path, @ApiParam(value = "show", required = true)@RequestParam("show") boolean isShow) {
        long i = 0;
        for (Long id : categoryService.getallChildsIdByPath(path)) {
            i+= bookService.getCountBooksByCategoryId(id, isShow);
        }
        return i;
    }
}