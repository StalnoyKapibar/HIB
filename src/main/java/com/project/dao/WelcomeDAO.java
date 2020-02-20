package com.project.dao;

import com.project.model.Welcome;

public interface WelcomeDAO {

    Welcome getWelcome(String locale);

    void setWelcome(Welcome welcome);


}


