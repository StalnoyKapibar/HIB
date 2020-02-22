package com.project.dao;

import com.project.model.LocaleString;
import com.project.model.Welcome;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;




@Repository
@Transactional
public class WelcomeDAOImp implements WelcomeDAO {

    @PersistenceContext
    private EntityManager entityManager;

//    public static void main(String[] args) {
//        new WelcomeDAOImp().getWelcome("ru");
//    }

    @Override
    public String getWelcome(String locale) {

        String temp = "Select w.body.LOC  FROM Welcome w".replaceAll("LOC", locale);
        return (String) entityManager.createQuery(temp).getSingleResult();
    }

    @Override
    public void setWelcome(Welcome welcome) {
        entityManager.persist(welcome);
    }
}
