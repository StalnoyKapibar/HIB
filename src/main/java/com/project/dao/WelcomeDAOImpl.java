package com.project.dao;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.*;

@Repository
public class WelcomeDAOImpl implements WelcomeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WelcomeLocaleDTO getWelcomeLocaleDTO(String locale) {
        String temp = "Select w.body.LOC  FROM Welcome w".replaceAll("LOC", locale);
        String loc = (String) entityManager.createQuery(temp).getSingleResult();
        WelcomeLocaleDTO welcomeLocaleDTO = modelMapper.map(loc, WelcomeLocaleDTO.class);
        welcomeLocaleDTO.setNameLocale(loc);
        return welcomeLocaleDTO;
    }

    @Override
    public void editWelcome(Welcome welcome) {
        entityManager.merge(welcome);
    }
}
