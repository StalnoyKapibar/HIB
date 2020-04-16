package com.project.dao;

import com.project.dao.abstraction.WelcomeDao;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class WelcomeDaoImpl extends AbstractDao<Long, Welcome> implements WelcomeDao {
    WelcomeDaoImpl(){super(Welcome.class);}

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(String locale) {
        String temp = "Select new com.project.model.WelcomeLocaleDTO('LOC', w.body.LOC) FROM Welcome w".replaceAll("LOC", locale);
        WelcomeLocaleDTO loc = entityManager.createQuery(temp, WelcomeLocaleDTO.class).getSingleResult();
        return loc;
    }
}
