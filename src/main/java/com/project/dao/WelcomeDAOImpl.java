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
        entityManager.createQuery("update LocaleString set ru =:ru, cs =:cs, de =:de, en =: en, fr =: fr, it =: it, gr =: gr")
                .setParameter("ru", welcome.getBody().getRu())
                .setParameter("cs", welcome.getBody().getCs())
                .setParameter("de", welcome.getBody().getDe())
                .setParameter("en", welcome.getBody().getEn())
                .setParameter("fr", welcome.getBody().getFr())
                .setParameter("it", welcome.getBody().getIt())
                .setParameter("gr", welcome.getBody().getGr())
                .executeUpdate();
    }
}
