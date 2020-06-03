package com.project.service;

import com.project.mail.MailService;
import com.project.model.Order;
import com.project.model.UserAccount;
import com.project.service.abstraction.SendEmailService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
@AllArgsConstructor
public class SendEmailServiceImpl implements SendEmailService {

    MailService mailService;

    Environment environment;

    @Override
    public void confirmAccount(UserAccount user) throws MessagingException {
        String senderFromProperty = environment.getProperty("spring.mail.username");
        Context context = new Context();
        context.setVariable("id", user.getTokenToConfirmEmail());
        MimeMessage message = mailService.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setTo(user.getEmail());
        helper.setSubject("Привет");
        helper.setFrom(senderFromProperty);
        helper.setText(mailService.getTemplate("mailForm/hello.html", context), true);
        mailService.sendEmail(message);
    }

    @Override
    public void confirmAccount1ClickReg(UserAccount user, String password, String login) throws MessagingException {
        Context context = new Context();
        context.setVariable("id", user.getTokenToConfirmEmail());
        context.setVariable("login", login);
        context.setVariable("password", password);
        String senderFromProperty = environment.getProperty("spring.mail.username");
        MimeMessage message = mailService.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setTo(user.getEmail());
        helper.setSubject("Привет");
        helper.setFrom(senderFromProperty);
        helper.setText(mailService.getTemplate("mailForm/oneClickReg.html", context), true);
        mailService.sendEmail(message);
    }

    @Override
    public void orderPresent(Order order) throws MessagingException {
        Context context = new Context();
        context.setVariable("orders", order);
        String senderFromProperty = environment.getProperty("spring.mail.username");
        MimeMessage message = mailService.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setTo(order.getUserAccount().getEmail());
        helper.setSubject("Заказ");
        helper.setFrom(senderFromProperty);
        helper.setText(mailService.getTemplate("mailForm/order.html", context), true);
        mailService.sendEmail(message);
    }
}