package com.project.service;


import com.project.model.Welcome;

public interface WelcomeService {

    String getWelcome(String locale);
    void setWelcome(Welcome welcome);

}
