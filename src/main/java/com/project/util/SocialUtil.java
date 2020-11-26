package com.project.util;

public class SocialUtil {
    public String getLoginFromEmail(String email) {
        char[] emailArr = new char[email.indexOf("@")];
        email.getChars(0, email.indexOf("@"), emailArr, 0);
        String login = new String(emailArr);
        return login;
    }
}
