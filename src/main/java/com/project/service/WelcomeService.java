package com.project.service;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;

public interface WelcomeService {
    WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(String locale);

    void editWelcome(Welcome welcome);
}
