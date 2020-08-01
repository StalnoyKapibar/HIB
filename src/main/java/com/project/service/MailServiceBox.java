package com.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

@Service
public class MailServiceBox {

//    Environment environment;
//
//    String IMAP_AUTH_EMAIL = environment.getProperty("spring.mail.username");
//    String IMAP_AUTH_PWD = environment.getProperty("spring.mail.password");
//    String IMAP_Server = environment.getProperty("spring.mail.imap");
//    String IMAP_Port = environment.getProperty("spring.mail.read.port");

//    @Autowired
//    private Authenticator auth;

//    public boolean readEmail() {
//        Properties properties = new Properties();
//        properties.put("mail.debug", "false");
//        properties.put("mail.store.protocol", "imaps");
//        properties.put("mail.imap.ssl.enable", "true");
//        properties.put("mail.imap.port", IMAP_Port);
//
//
//        Session session = Session.getDefaultInstance(properties, auth);
//        session.setDebug(false);
//
//        try {
//            Store store = session.getStore();
//
//            // Подключение к почтовому серверу
//            store.connect(IMAP_Server, IMAP_AUTH_EMAIL, IMAP_AUTH_PWD);
//
//            // Папка входящих сообщений
//            Folder inbox = store.getFolder("INBOX");
//
//            // Открываем папку в режиме только для чтения
//            inbox.open(Folder.READ_ONLY);
//
//            Message[] messages = inbox.getMessages();
//
//            for (Message message : messages) {
//                for (Address address1 : message.getAllRecipients()) {
//                    if (address1.toString().equals("tetanmax@yandex.ru")) {
//                        System.out.println("if is okay");
//                        return true;
//                    }
//                }
//            }
//
//        } catch (MessagingException e) {
//            System.err.println(e.getMessage());
//        }
//        return false;
//    }
}
