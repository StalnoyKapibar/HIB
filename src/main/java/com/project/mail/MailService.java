package com.project.mail;

import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.context.Context;
import javax.mail.internet.MimeMessage;

public interface MailService {
    void sendEmail(SimpleMailMessage email, String emailTo);

    void sendEmail(MimeMessage email, String emailTo);

    String getTemplate(String template, Context context );

    MimeMessage createMimeMessage();
}
