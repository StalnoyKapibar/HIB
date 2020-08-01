package com.project.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;

    private TemplateEngine templateEngine;

    private MailCheckSent mailCheckSent;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Autowired
    public void setMailCheckSent(MailCheckSent mailCheckSent) {
        this.mailCheckSent = mailCheckSent;
    }

    @Async
    public void sendEmail(SimpleMailMessage email, String emailTo) {
        do {
            try {
                javaMailSender.send(email);
                Thread.sleep(60000);
            } catch (MailSendException | InterruptedException e) {
                e.printStackTrace();
            }
        } while (!mailCheckSent.isMailSent(emailTo));
    }

    @Async
    public void sendEmail(MimeMessage email, String emailTo) {
        do {
            try {
                javaMailSender.send(email);
                Thread.sleep(60000);
            } catch (MailSendException | InterruptedException e) {
                e.printStackTrace();
            }
        } while (!mailCheckSent.isMailSent(emailTo));
    }

    @Override
    public MimeMessage createMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    public String getTemplate(String template, Context context ){
        return templateEngine.process(template, context);
    }
}