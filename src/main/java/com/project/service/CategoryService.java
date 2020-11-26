package com.project.service;

import com.project.dao.CategoryDAO;
import com.project.model.Category;
import com.project.model.LocaleString;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    CategoryDAO categoryDAO;

    public void addCategory(Category category) {
        categoryDAO.addCategory(category);
    }

    public List getCategoryTree(String loc) {
        return categoryDAO.getCategoryTree(loc);
    }

    public List getAdminCategoryTree() {
        return categoryDAO.getAdminCategoryTree();
    }

    public List<Long> getNoParentCategoriesById() {
        return categoryDAO.getNoParentCategoriesById();
    }

    public void update(Category category) {
        categoryDAO.update(category);
    }

    public void delete(Long id) {
        categoryDAO.deleteById(id);
    }

    public List getChildWithCTE(Long id) {
        return categoryDAO.getChildWithCTE(id);
    }

    public List<Long> getallChildsIdByPath(String path) {
        List<Long> ids = new ArrayList<>();
        for (Object id : categoryDAO.getAllChildsIdByPath(path)) {
            ids.add(Long.parseLong(String.valueOf(id)));
        }
        return ids;
    }

    public void parentChange(Category category) {
        Long id = category.getId();
        Long parentId = category.getParentId();
        categoryDAO.parentChange(id, parentId);
    }

    public List<Category> getListCategoriesById(String local) {
        return categoryDAO.getListCategoriesById(local, categoryDAO.getNoParentCategoriesById());
    }

    public Category getCategoryById(Long id) {
        return categoryDAO.findById(id);
    }
}
