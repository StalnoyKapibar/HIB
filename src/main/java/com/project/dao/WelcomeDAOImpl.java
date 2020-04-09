package com.project.dao;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class    WelcomeDAOImpl implements WelcomeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(String locale) {
        String temp = "Select new com.project.model.WelcomeLocaleDTO('LOC', w.body.LOC) FROM Welcome w".replaceAll("LOC", locale);
        WelcomeLocaleDTO loc = entityManager.createQuery(temp, WelcomeLocaleDTO.class).getSingleResult();
        return loc;
    }

    @Override
    public void editWelcome(Welcome welcome) {
        if (welcome.getId() == null) {
            entityManager.persist(welcome);
        } else {
            entityManager.merge(welcome);
        }
    }

    @Override
    public Welcome getById(Long id) {
        return entityManager.find(Welcome.class, id);
    }
}
