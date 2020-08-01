package com.project.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.Properties;

/**
 * Checking the Outbox folder if the message was sent
 */

@Service
@Scope("prototype")
public class MailCheckSent {

    private final String IMAP_AUTH_EMAIL;
    private final String IMAP_AUTH_PWD;
    private final String IMAP_Server;
    private final String IMAP_Port;

    @Autowired
    public MailCheckSent(Environment env) {
        IMAP_AUTH_EMAIL = env.getProperty("spring.mail.username");
        IMAP_AUTH_PWD = env.getProperty("spring.mail.password");
        IMAP_Server = env.getProperty("spring.mail.imap");
        IMAP_Port = env.getProperty("spring.mail.imapport");
    }

    public boolean isMailSent(String email) {
        Properties properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", IMAP_Port);
        Session session = Session.getDefaultInstance(properties, null);

        try {
            Store store = session.getStore("imaps");

            // Connect to the server
            store.connect(IMAP_Server, IMAP_AUTH_EMAIL, IMAP_AUTH_PWD);

            // Checkout outbox folder
            Folder inbox = store.getFolder("[Gmail]").getFolder("Sent Mail");
            inbox.open(Folder.READ_ONLY);

            Message message = inbox.getMessage(inbox.getMessageCount());
            if (message.getAllRecipients()[message.getAllRecipients().length - 1].toString().equalsIgnoreCase(email)) {
                return true;
            }
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}