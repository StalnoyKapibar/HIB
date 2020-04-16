package com.project.dao.abstraction;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;

public interface WelcomeDao extends IGenericDao<Long, Welcome>{
    WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(String locale);
}


