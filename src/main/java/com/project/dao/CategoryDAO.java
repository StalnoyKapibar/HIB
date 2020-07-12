package com.project.dao;

import com.project.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
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
                "    SELECT d.id, oi.en, CAST(LOWER(CONCAT('/', d.name_id)) AS CHAR(1000)) AS path, d.parent_id, d.view_order\n" +
                "    FROM category d JOIN local_string oi on d.name_id = oi.id where d.parent_id IS NULL\n" +
                "    UNION ALL\n" +
                "    SELECT c.id, oi.en, CONCAT(cte.path, '/', LOWER(c.name_id)), c.parent_id, c.view_order\n" +
                "    FROM cte cte, category c JOIN local_string oi on c.name_id = oi.id\n" +
                "    WHERE c.parent_id = cte.id\n" +
                "    )\n" +
                "SELECT * FROM cte ORDER BY view_order;";
        return entityManager.createNativeQuery(hql).getResultList();
    }

    public List getCategoryTree() {
        String hql = "WITH RECURSIVE cte AS\n" +
                "    (\n" +
                "    SELECT d.id, oi.en, CAST(LOWER(CONCAT('/', d.name_id)) AS CHAR(1000)) AS path, d.parent_id, d.view_order\n" +
                "    FROM category d JOIN local_string oi on d.name_id = oi.id where parent_id IS NULL\n" +
                "    UNION ALL\n" +
                "    SELECT c.id, oi.en, CONCAT(cte.path, '/', LOWER(c.name_id)), c.parent_id, c.view_order\n" +
                "    FROM cte cte, category c JOIN local_string oi on c.name_id = oi.id\n" +
                "    WHERE c.parent_id = cte.id\n" +
                "    )\n" +
                "SELECT * FROM cte ORDER BY view_order";
        return entityManager.createNativeQuery(hql).getResultList();
    }

    public List<Long> getIdsCategories () {
        return entityManager.createQuery("SELECT c.id FROM Category c", Long.class).getResultList();
    }

    public List<Long> getNoParentCategoriesById() {
        String hql = "SELECT c.name.id FROM Category c WHERE c.parentId IS NULL ORDER BY c.viewOrder";
        return entityManager.createQuery(hql, Long.class).setMaxResults(4).getResultList();
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
                "SELECT c.id, oi.en, CAST(LOWER(CONCAT('/', c.name_id)) AS CHAR(1000)) AS path\n" +
                "FROM category c JOIN local_string oi on c.name_id = oi.id where parent_id IS NULL\n" +
                "UNION ALL\n" +
                "SELECT c.id, oi.en, CONCAT(cte.path, '/', LOWER(c.name_id))\n" +
                "FROM cte cte, category c JOIN local_string oi on name_id = oi.id\n" +
                "WHERE c.parent_id = cte.id\n" +
                ")\n" +
                "SELECT id FROM cte WHERE path LIKE '%/path%' ORDER BY path".replace("/path", path);

        return entityManager.createNativeQuery(sql).getResultList();
    }

    public List getAllChildsIdByParentId(Long parentId) {
        String sql = "WITH RECURSIVE cte AS\n" +
                "(\n" +
                "SELECT id, oi.en, 0 AS stack\n" +
                "FROM category where parent_id = :parentId JOIN local_string oi on c.name_id = oi.id\n" +
                "UNION ALL\n" +
                "SELECT c.id, oi.en, cte.stack + 1\n" +
                "FROM cte cte, category c JOIN local_string oi on c.name_id = oi.id\n" +
                "WHERE c.parent_id = cte.id\n" +
                ")\n" +
                "SELECT id FROM cte order by id, stack";

        return  entityManager.createNativeQuery(sql).setParameter("parentId", parentId).getResultList();
    }

    public List<Category> getListCategoriesById(String local, List<Long> categories) {
        String hql = ("SELECT b.name." + local + " FROM Category b WHERE b.name.id IN :list");
        Query list = entityManager.createQuery(hql)
                .setParameter("list", categories);
        return list.getResultList();
    }

    public void parentChange(Long id, Long parentId) {
        String sql = "UPDATE category SET parent_id =:parentId WHERE id =:id";
        entityManager.createNativeQuery(sql).setParameter("id", id).setParameter("parentId", parentId).executeUpdate();
    }
}
