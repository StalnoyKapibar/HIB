package com.project.dao;

import com.project.model.Welcome;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;


@Repository
@Transactional
public class WelcomeDAOImp implements WelcomeDAO {
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public Welcome getWelcome() {
        return (Welcome) entityManager.createQuery("FROM Welcome").getSingleResult();
    }

    @Override
    public void setWelcome(Welcome welcome) {
        entityManager.persist(welcome);
    }
}
