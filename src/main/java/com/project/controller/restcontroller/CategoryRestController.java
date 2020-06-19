package com.project.controller.restcontroller;

import com.project.model.*;
import com.project.service.CategoryService;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.LocalStringService;
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

    LocalStringService localStringService;

    @GetMapping("/getpanelcategories/{name}")
    public List<LocaleString> getPanelCategories(@PathVariable("name") String name) {
        return localStringService.getLocalString(name, categoryService.getNoParentCategories());
    }

    @GetMapping("/gettree")
    @Cacheable(value = "categoryTree")
    public List getCategoryTree() {
        return categoryService.getCategoryTree();
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
    public Long getCountBooksByPath(@RequestParam("path") String path) {
        long i = 0;
        for (Long id : categoryService.getallChildsIdByPath(path)) {
            i+= bookService.getCountBooksByCategoryId(id);
        }
        return i;
    }

}

