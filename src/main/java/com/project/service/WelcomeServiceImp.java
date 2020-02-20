package com.project.service;


import com.project.dao.WelcomeDAO;
import com.project.model.Welcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImp implements WelcomeService {


    @Autowired
    private WelcomeDAO welcomeDAO;


    @Override
    public Welcome getWelcome() {
        return welcomeDAO.getWelcome("ru");
    }

    @Override
    public void setWelcome(Welcome welcome) {
        welcomeDAO.setWelcome(welcome);
    }


}
