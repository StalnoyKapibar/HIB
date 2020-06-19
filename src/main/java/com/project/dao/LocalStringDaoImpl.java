package com.project.dao;

import com.project.dao.abstraction.LocalStringDao;
import com.project.model.Category;
import com.project.model.LocaleString;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LocalStringDaoImpl implements LocalStringDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addLocalString(LocaleString localeString) {
        entityManager.persist(localeString);
    }

    @Override
    public List<LocaleString> getLocalString(String name, List<Category> categories) {
        String hql = "SELECT " + name + " FROM LocaleString where en = '" + categories.get(0).getCategoryName() + "' or en = '" + categories.get(1).getCategoryName() + "' or en = '" + categories.get(2).getCategoryName() + "' or en = '" + categories.get(3).getCategoryName() + "'";
        return entityManager.createQuery(hql).getResultList();
    }
}
