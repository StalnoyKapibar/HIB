package com.project.controller.restcontroller.emailUtil.emailParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GmailParser implements EmailParser {
    @Override
    public String getMessageTextWithoutRe(String text) {
        Pattern pattern = Pattern.compile("<div class=\"gmail_quote.+</blockquote>");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String re = text.substring(text.indexOf("<div class=\"gmail_quote"), text.indexOf("</blockquote></div>")+19);
            text = text.replace(re, "");
        }
        return text;
    }
}
