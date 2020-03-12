package com.project.service;

import com.project.dao.WelcomeDAO;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WelcomeServiceImpl implements WelcomeService {

    @Autowired
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
