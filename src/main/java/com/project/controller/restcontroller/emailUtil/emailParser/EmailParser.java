package com.project.controller.restcontroller.emailUtil.emailParser;

public interface EmailParser {
    public static EmailParser getInstance(String email) {
        switch (email.substring(email.indexOf("@") + 1)) {
            case "me":
            case "gmail.com":
                return new GmailParser();
            case "mail.ru":
                return new MailRuParser();
            case "yandex.ru":
                return new YandexRuParser();
            default:
                return new DefaultParser();
        }
    }
    public String getMessageTextWithoutRe(String text);
}
