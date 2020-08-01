package com.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

//@Configuration
//public class EmailAuthenticator extends Authenticator {
//
//    Environment environment;
//
//    private String login = environment.getProperty("spring.mail.username");
//    private String password = environment.getProperty("spring.mail.password");
//
//    public PasswordAuthentication getPasswordAuthentication() {
//        return new PasswordAuthentication(login, password);
//    }
//}