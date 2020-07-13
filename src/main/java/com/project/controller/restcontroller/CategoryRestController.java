package com.project.controller.restcontroller;

import com.project.model.*;
import com.project.service.CategoryService;
import com.project.service.abstraction.BookService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryRestController {

    CategoryService categoryService;

    BookService bookService;

    @GetMapping("/getpanelcategories/{name}")
    public List<Category> getPanelCategories(@PathVariable("name") String name) {
        List<Category> list =  categoryService.getListCategoriesById(name);

        return list;
    }

    @GetMapping("/gettree/{loc}")
    @Cacheable(value = "categoryTree")
    public List getCategoryTree(@PathVariable("loc") String loc) {
        List list = categoryService.getCategoryTree(loc);
        return list;
    }

    @GetMapping("/getbooks")
    public List<BookDTOForCategories> getBooksByPath(@RequestParam("path") String path) {
        List<BookDTOForCategories> books = new ArrayList<>();
        for (Long id : categoryService.getallChildsIdByPath(path)) {
            books.addAll(bookService.getBooksByCategoryId(id, "en"));
        }
        return books;
    }

    @GetMapping("/getcount")
    public Long getCountBooksByPath(@RequestParam("path") String path, @RequestParam("show") boolean isShow) {
        long i = 0;
        for (Long id : categoryService.getallChildsIdByPath(path)) {
            i+= bookService.getCountBooksByCategoryId(id, isShow);
        }
        return i;
    }

}

