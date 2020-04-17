package com.project.dao;

import com.project.dao.abstraction.WelcomeDao;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDto;
import org.springframework.stereotype.Repository;

@Repository
public class WelcomeDaoImpl extends AbstractDao<Long, Welcome> implements WelcomeDao {
    WelcomeDaoImpl() {
        super(Welcome.class);
    }

    @Override
    public WelcomeLocaleDto getWelcomeLocaleDTOByLocale(String locale) {
        String temp = "Select new com.project.model.WelcomeLocaleDTO('LOC', w.body.LOC) FROM Welcome w".replaceAll("LOC", locale);
        WelcomeLocaleDto loc = entityManager.createQuery(temp, WelcomeLocaleDto.class).getSingleResult();
        return loc;
    }
}
