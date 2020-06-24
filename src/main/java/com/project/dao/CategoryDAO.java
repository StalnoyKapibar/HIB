package com.project.dao;

import com.project.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDAO extends AbstractDao<Long, Category> {

    CategoryDAO(){
        super(Category.class);
    }

    @PersistenceContext
    EntityManager entityManager;

    public void addCategory(Category category) {
        entityManager.persist(category);
    }

    public List getAdminCategoryTree() {
        String hql = "WITH RECURSIVE cte AS\n" +
                "    (\n" +
                "    SELECT id, category_name, CAST(LOWER(CONCAT('/', category_name)) AS CHAR(1000)) AS path, parent_id, view_order\n" +
                "    FROM category where parent_id IS NULL\n" +
                "    UNION ALL\n" +
                "    SELECT c.id, c.category_name, CONCAT(cte.path, '/', LOWER(c.category_name)), c.parent_id, c.view_order\n" +
                "    FROM cte cte, category c\n" +
                "    WHERE c.parent_id = cte.id\n" +
                "    )\n" +
                "SELECT * FROM cte ORDER BY view_order;";
        return entityManager.createNativeQuery(hql).getResultList();
    }

    public List getCategoryTree() {
        String hql = "WITH RECURSIVE cte AS\n" +
                "    (\n" +
                "    SELECT id, category_name, CAST(LOWER(CONCAT('/', category_name)) AS CHAR(1000)) AS path, parent_id, view_order\n" +
                "    FROM category where parent_id IS NULL\n" +
                "    UNION ALL\n" +
                "    SELECT c.id, c.category_name, CONCAT(cte.path, '/', LOWER(c.category_name)), c.parent_id, c.view_order\n" +
                "    FROM cte cte, category c\n" +
                "    WHERE c.parent_id = cte.id\n" +
                "    )\n" +
                "SELECT * FROM cte ORDER BY view_order";
        return entityManager.createNativeQuery(hql).getResultList();
    }

    public List<Category> getNoParentCategories() {
        String hql = "SELECT c FROM Category c WHERE c.parentId IS NULL ORDER BY c.viewOrder";
        return entityManager.createQuery(hql, Category.class).getResultList();
    }

    public List<Category> getCategories () {
        String hql = "SELECT c FROM Category c";
        return entityManager.createQuery(hql, Category.class).getResultList();
    }


    @Override
    public Category update(Category entity) {
        return super.update(entity);
    }

    public List getChildWithCTE(Long id) {
        String sql = "WITH RECURSIVE cte AS" +
                    "( " +
                    "SELECT id " +
                    "FROM category " +
                    "WHERE id=:id " +
                    "UNION ALL " +
                    "SELECT c.id " +
                    "FROM cte d, category c " +
                    "WHERE c.parent_id=d.id " +
                    ") " +
                    "SELECT * FROM cte";
        return entityManager.createNativeQuery(sql).setParameter("id", id).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    public List getAllChildsIdByPath(String path) {
        String sql = "WITH RECURSIVE cte AS\n" +
                "(\n" +
                "SELECT id, category_name, CAST(LOWER(CONCAT('/', category_name)) AS CHAR(1000)) AS path\n" +
                "FROM category where parent_id IS NULL\n" +
                "UNION ALL\n" +
                "SELECT c.id, c.category_name, CONCAT(cte.path, '/', LOWER(c.category_name))\n" +
                "FROM cte cte, category c\n" +
                "WHERE c.parent_id = cte.id\n" +
                ")\n" +
                "SELECT id FROM cte WHERE path LIKE '%/path%' ORDER BY path".replace("/path", path);

        return entityManager.createNativeQuery(sql).getResultList();
    }

    public List getAllChildsIdByParentId(Long parentId) {
        String sql = "WITH RECURSIVE cte AS\n" +
                "(\n" +
                "SELECT id, category_name, 0 AS stack\n" +
                "FROM category where parent_id = :parentId\n" +
                "UNION ALL\n" +
                "SELECT c.id, c.category_name, cte.stack + 1\n" +
                "FROM cte cte, category c\n" +
                "WHERE c.parent_id = cte.id\n" +
                ")\n" +
                "SELECT id FROM cte order by id, stack";

        return  entityManager.createNativeQuery(sql).setParameter("parentId", parentId).getResultList();
    }

    public void parentChange(Long id, Long parentId) {
        String sql = "UPDATE category SET parent_id =:parentId WHERE id =:id";
        entityManager.createNativeQuery(sql).setParameter("id", id).setParameter("parentId", parentId).executeUpdate();
    }
}
