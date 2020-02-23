package com.project.dao;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;

public interface WelcomeDAO {
    WelcomeLocaleDTO getWelcomeLocaleDTO(String locale);
    void editWelcome(Welcome welcome);
}


