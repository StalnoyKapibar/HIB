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

}
