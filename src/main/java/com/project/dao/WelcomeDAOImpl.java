package com.project.dao;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.*;

@Repository
public class WelcomeDAOImpl implements WelcomeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(String locale) {
        String temp = "Select w.body.LOC  FROM Welcome w".replaceAll("LOC", locale);
        String loc = (String) entityManager.createQuery(temp).getSingleResult();
        WelcomeLocaleDTO welcomeLocaleDTO0 = new WelcomeLocaleDTO(locale, loc);
        return welcomeLocaleDTO0;
    }

    @Override
    public void editWelcome(Welcome welcome) {
        entityManager.merge(welcome);
    }
}
