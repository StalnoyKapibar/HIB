package com.project.service;

import com.project.dao.CategoryDAO;
import com.project.model.Category;
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

    public List getCategoryTree() {
        return categoryDAO.getCategoryTree();
    }

    public List getAdminCategoryTree() {
        return categoryDAO.getAdminCategoryTree();
    }

    public List<Category> getNoParentCategories() {
        return categoryDAO.getNoParentCategories();
    }

    public void update(Category category) {
        categoryDAO.update(category);
    }

    public void delete(Category category) {
        for (Object id : categoryDAO.getChildWithCTE(category.getId())) {
            categoryDAO.deleteById(Long.parseLong(String.valueOf(id)));
        }
    }

    public List<Long> getallChildsIdByPath(String path) {
        List<Long> ids = new ArrayList<>();
        for (Object id : categoryDAO.getAllChildsIdByPath(path)) {
            ids.add(Long.parseLong(String.valueOf(id)));
        }
        return ids;
    }

}
