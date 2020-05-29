package com.project.mail;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface MailService {
    void sendEmail(SimpleMailMessage email);

    void sendEmail(MimeMessage email);

    MimeMessage createMimeMessage();

}
