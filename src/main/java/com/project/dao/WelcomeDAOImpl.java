package com.project.dao;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Repository
public class WelcomeDAOImpl implements WelcomeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(String locale) {
        String temp = "Select new com.project.model.WelcomeLocaleDTO('LOC', w.body.LOC) FROM Welcome w".replaceAll("LOC", locale);
        WelcomeLocaleDTO loc = entityManager.createQuery(temp, WelcomeLocaleDTO.class).getSingleResult();
        return loc;
    }

    @Override
    public void editWelcome(Welcome welcome) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("update LocaleString set ru =:ru, cs =:cs, de =:de, en =: en, fr =: fr, it =: it")
                .setParameter("ru", welcome.getBody().getRu())
                .setParameter("cs", welcome.getBody().getCs())
                .setParameter("de", welcome.getBody().getDe())
                .setParameter("en", welcome.getBody().getEn())
                .setParameter("fr", welcome.getBody().getFr())
                .setParameter("it", welcome.getBody().getIt())
                .executeUpdate();
        em.getTransaction().commit();
    }
}
