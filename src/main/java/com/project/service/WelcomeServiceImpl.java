package com.project.service;

import com.project.dao.abstraction.WelcomeDao;
import com.project.model.Welcome;
import com.project.model.WelcomeLocaleDto;
import com.project.service.abstraction.WelcomeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class WelcomeServiceImpl implements WelcomeService {

    private WelcomeDao welcomeDAO;

    @Override
    public WelcomeLocaleDto getWelcomeLocaleDTOByLocale(String locale) {
        return welcomeDAO.getWelcomeLocaleDTOByLocale(locale);
    }

    @Override
    public void editWelcome(Welcome welcome) {
        if (welcome.getId() == null) {
            welcomeDAO.add(welcome);
        } else {
            welcomeDAO.update(welcome);
        }
    }

    @Override
    public Welcome getById(Long id) {
        return welcomeDAO.findById(id);
    }
}
