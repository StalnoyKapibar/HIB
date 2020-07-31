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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

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
    public void sendEmail(MimeMessage email, String to) {
        try {
            do {
                javaMailSender.send(email);
                Thread.sleep(10000);
            } while (!getSentEmails(to));
        } catch (MailSendException | MessagingException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public boolean getSentEmails(String to) throws MessagingException {
        Properties properties = new Properties();
        //Так как для чтения Yandex требует SSL-соединения - нужно использовать фабрику SSL-сокетов
        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Создаем соединение для чтения почтовых сообщений
        Session session = Session.getDefaultInstance(properties);
        //Это хранилище почтовых сообщений. По сути - это и есть почтовый ящик=)
        Store store = null;
        try {
            //Для чтения почтовых сообщений используем протокол IMAP.
            //Почему? Так Yandex сказал: https://yandex.ru/support/mail/mail-clients.html
            //см. раздел "Входящая почта"
            store = session.getStore("imap");
            //Подключаемся к почтовому ящику
            store.connect("smtp.yandex.ru", 465, "hibthebestproject", "1357924680");
            //Это папка, которую будем читать
            Folder sent = null;
            try {
                //Читаем папку "Входящие сообщения"
                sent = store.getFolder("SENT");
                //Будем только читать сообщение, не меняя их
                sent.open(Folder.READ_ONLY);

                //Получаем количество сообщения в папке
                int count = sent.getMessageCount();
                //Вытаскиваем все сообщения с первого по последний
                Message[] messages = sent.getMessages(1, count);
                //Циклом пробегаемся по всем сообщениям
                for (Message message : messages) {
                    //От кого
                    String emailTo = ((InternetAddress) message.getFrom()[0]).getAddress();
                    if (emailTo.equalsIgnoreCase(to)) {
                        return true;
                    }
                    System.out.println("To: " + emailTo);
                    //Тема письма
//                    System.out.println("SUBJECT: " + message.getSubject());
                }
            } finally {
                if (sent != null) {
                    //Не забываем закрыть собой папку сообщений.
                    sent.close(false);
                }
            }

        } finally {
            if (store != null) {
                //И сам почтовый ящик тоже закрываем
                store.close();
            }
        }

        return false;
    }

    @Override
    public MimeMessage createMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    public String getTemplate(String template, Context context ){
        return templateEngine.process(template, context);
    }

}