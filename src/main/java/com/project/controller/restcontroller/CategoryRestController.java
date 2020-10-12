package com.project.controller.restcontroller;

import com.project.model.BookDTOForCategories;
import com.project.model.Category;
import com.project.model.CategoryDTO;
import com.project.service.CategoryService;
import com.project.service.abstraction.BookService;
import lombok.AllArgsConstructor;
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
        return categoryService.getListCategoriesById(name);
    }

    @GetMapping("/gettree/{loc}")
    public List getCategoryTree(@PathVariable("loc") String loc) {
        return categoryService.getCategoryTree(loc);
    }

    @GetMapping("/gettree2/{loc}/{show}")
    public List<CategoryDTO> getCategoryTree2(@PathVariable("loc") String loc, @PathVariable("show") boolean isShow) {
        // isShow - нужна для того, что бы показывать количество всех книг и при true - количество книг доступных
        List<CategoryDTO> list = CategoryDTO.transformObj(categoryService.getCategoryTree(loc));
        for (CategoryDTO catDTO : list) {
            long i = 0;
            for (Long id : categoryService.getallChildsIdByPath(catDTO.getPath())) {
                i += bookService.getCountBooksByCategoryId(id, isShow);
            }
            catDTO.setBooksCount(i);
        }
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
            i += bookService.getCountBooksByCategoryId(id, isShow);
        }
        return i;
    }
}