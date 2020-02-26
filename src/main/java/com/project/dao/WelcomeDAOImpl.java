package com.project.dao;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Transactional
@Repository
public class WelcomeDAOImpl implements WelcomeDAO {

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
        entityManager.merge(welcome);
    }
}
