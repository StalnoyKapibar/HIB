package com.project.controller.restcontroller;

import com.project.model.*;
import com.project.service.CategoryService;
import com.project.service.abstraction.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryRestController {

    CategoryService categoryService;

    BookService bookService;

    @GetMapping("/getnullparent")
    public List<Category> getNoParentCategories() {
        return categoryService.getNoParentCategories();
    }

    @GetMapping("/gettree")
    public List getCategoryTree() {
        return categoryService.getCategoryTree();
    }

    @GetMapping("/getbooks")
    public List<BookDTOForCategories> getbooks(@RequestParam("path") String path) {
        List<BookDTOForCategories> books = new ArrayList<>();
        for (Long id : categoryService.getallChildsIdByPath(path)) {
            books.addAll(bookService.getBooksByCategoryId(id, "en"));
        }
        return books;
    }

}

