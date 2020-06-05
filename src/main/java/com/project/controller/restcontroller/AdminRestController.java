package com.project.controller.restcontroller;

import com.project.model.Category;
import com.project.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminRestController {

    CategoryService categoryService;

    /* Categories */
    @PostMapping("/categories/addcategory")
    @CacheEvict("categoryTree")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @GetMapping("/categories/getadmintree")
    public List getAdminCategoryTree() {
        return categoryService.getAdminCategoryTree();
    }

    @PostMapping("/categories/delete")
    @CacheEvict("categoryTree")
    public void delete(@RequestBody Category category) {
        categoryService.delete(category);
    }

    @PutMapping("/categories/update")
    @CacheEvict("categoryTree")
    public void update(@RequestBody Category category) {
        categoryService.update(category);
    }

    @PostMapping("/categories/add")
    @CachePut("categoryTree")
    public void add(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @PostMapping("/categories/parentchange")
    @CacheEvict("categoryTree")
    public void parentChange(@RequestBody Category category) {
        categoryService.parentChange(category);
    }

}
