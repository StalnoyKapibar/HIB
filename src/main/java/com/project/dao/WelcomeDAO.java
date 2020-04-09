package com.project.dao;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;

public interface WelcomeDAO {
    WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(String locale);

    void editWelcome(Welcome welcome);

    Welcome getById(Long id);
}


