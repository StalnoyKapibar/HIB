package com.project.service;

import com.project.dao.WelcomeDAO;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class WelcomeServiceImpl implements WelcomeService {


    private WelcomeDAO welcomeDAO;

    @Override
    public WelcomeLocaleDTO getWelcomeLocaleDTOByLocale(String locale) {
        return welcomeDAO.getWelcomeLocaleDTOByLocale(locale);
    }

    @Override
    public void editWelcome(Welcome welcome) {
        welcomeDAO.editWelcome(welcome);
    }
}
