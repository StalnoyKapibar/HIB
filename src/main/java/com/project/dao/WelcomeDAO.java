package com.project.dao;

import com.project.model.Welcome;

public interface WelcomeDAO {

    String getWelcome(String locale);

    void setWelcome(Welcome welcome);


}


