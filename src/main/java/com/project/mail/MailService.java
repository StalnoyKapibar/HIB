package com.project.mail;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {
    void sendEmail(SimpleMailMessage email);
}
