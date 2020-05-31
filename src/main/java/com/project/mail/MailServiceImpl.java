package com.project.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Objects;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;

    private TemplateEngine templateEngine;

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
        javaMailSender.send(email);
    }

    @Override
    public MimeMessage createMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    public String getTemplate(String template, Context context ){
        return templateEngine.process(template, context);
    }

}
