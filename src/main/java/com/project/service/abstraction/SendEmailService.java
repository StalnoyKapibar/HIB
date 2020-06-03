package com.project.service.abstraction;

import com.project.model.Order;
import com.project.model.UserAccount;

import javax.mail.MessagingException;

public interface SendEmailService {

    void confirmAccount(UserAccount user) throws MessagingException;

    void confirmAccount1ClickReg(UserAccount user, String password, String login) throws MessagingException;

    void orderPresent(Order order) throws MessagingException;
}