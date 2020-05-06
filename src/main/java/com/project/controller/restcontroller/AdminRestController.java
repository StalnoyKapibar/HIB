package com.project.controller.restcontroller;

import com.project.model.Category;
import com.project.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminRestController {

    CategoryService categoryService;

    /* Categories */
    @PostMapping("/categories/addcategory")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @GetMapping("/categories/getadmintree")
    public List getAdminCategoryTree() {
        return categoryService.getAdminCategoryTree();
    }

    @PostMapping("/categories/delete")
    public void delete(@RequestBody Category category) {
        categoryService.delete(category);
    }

    @PutMapping("/categories/update")
    public void update(@RequestBody Category category) {
        categoryService.update(category);
    }

    @PostMapping("/categories/add")
    public void add(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @PostMapping("/categories/parentchange")
    public void parentChange(@RequestBody Category category) {
        categoryService.parentChange(category);
    }

}
