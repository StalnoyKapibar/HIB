package com.project.service.abstraction;

import com.project.model.Order;
import com.project.model.OrderDTO;
import com.project.model.UserAccount;

import javax.mail.MessagingException;

public interface SendEmailService {

    void confirmAccount(UserAccount user, String url) throws MessagingException;

    void confirmAccount1ClickReg(UserAccount user, String password, String login, String url, OrderDTO order) throws MessagingException;

    void orderPresent(Order order, String url) throws MessagingException;
}