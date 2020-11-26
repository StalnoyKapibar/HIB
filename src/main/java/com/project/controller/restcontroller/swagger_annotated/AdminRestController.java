package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.Book;
import com.project.model.Category;
import com.project.service.CategoryService;
import com.project.service.abstraction.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Api(tags = "REST-API документ, " +
        "описывающий сервис взаимодействия с категориями книг.")
@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminRestController {

    CategoryService categoryService;
    BookService bookService;

    /* Categories */
    @ApiOperation(value = "Создать новую категорию книг",
            notes = "Эндпоинт получает параметр category типа Category")
    @PostMapping("/categories/addcategory")
    @CacheEvict("categoryTree")
    public void addCategory(@ApiParam(value = "Category", required = true) @RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @ApiOperation(value = "Получить древо категорий для локали EN",
            notes = "Этот эндпойнт вернёт list с категориями",
            response = Category.class,
            responseContainer = "List",
            tags = "getAdminCategoryTree")
    @GetMapping("/categories/getadmintree")
    public List getAdminCategoryTree() {
        return categoryService.getAdminCategoryTree();
    }

    @ApiOperation(value = "Удалить категорию",
            notes = "Эндпоинт получает параметр category типа Category")
    @PostMapping("/categories/delete")
    @CacheEvict("categoryTree")
    public void delete(@ApiParam(value = "Category", required = true) @RequestBody Category category) {
        if (category.getId() != 1) {
            for (Object id : categoryService.getChildWithCTE(category.getId())) {
                Long categoryId = Long.parseLong(String.valueOf(id));
                for (Book book : bookService.getAllBooksByCategoryId(categoryId)) {
                    book.setCategory(categoryService.getCategoryById(1L));
                    bookService.updateBook(book);
                }
                categoryService.delete(categoryId);
            }
        }
    }

    @ApiOperation(value = "Изменить категорию книг",
            notes = "Эндпоинт получает параметр category типа Category")
    @PutMapping("/categories/update")
    @CacheEvict("categoryTree")
    public void update(@ApiParam(value = "Category", required = true) @RequestBody Category category) {
        categoryService.update(category);
    }

    @ApiOperation(value = "Создать новую категорию книг",
            notes = "Эндпоинт получает параметр category типа Category")
    @PostMapping("/categories/add")
    @CachePut("categoryTree")
    public void add(@ApiParam(value = "Category", required = true) @RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @ApiOperation(value = "Сохранить добавленный parrentId к категории",
            notes = "Эндпоинт получает параметр category типа Category")
    @PostMapping("/categories/parentchange")
    @CacheEvict("categoryTree")
    public void parentChange(@ApiParam(value = "Category", required = true) @RequestBody Category category) {
        categoryService.parentChange(category);
    }

}