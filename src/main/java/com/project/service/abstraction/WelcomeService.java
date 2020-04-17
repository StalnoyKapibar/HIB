package com.project.service.abstraction;

import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDto;

public interface WelcomeService {
    WelcomeLocaleDto getWelcomeLocaleDTOByLocale(String locale);

    void editWelcome(Welcome welcome);

    Welcome getById(Long id);
}
