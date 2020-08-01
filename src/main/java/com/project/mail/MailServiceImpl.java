package com.project.mail;

import com.project.service.MailServiceBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;

    private TemplateEngine templateEngine;

    @Autowired
    private MailServiceBox mailServiceBox;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) throws MailSendException {

        javaMailSender.send(email);
    }

    @Async
    public void sendEmail(MimeMessage email) {

        try {
            //mailServiceBox.readEmail();

            javaMailSender.send(email);

            Thread.sleep(10000);
        } catch (MailSendException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MimeMessage createMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    public String getTemplate(String template, Context context) {
        return templateEngine.process(template, context);
    }
}