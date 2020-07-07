package com.project.controller.restcontroller.emailUtil.emailParser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailRuParser implements EmailParser {
    @Override
    public String getMessageTextWithoutRe(String text) {
        Pattern pattern = Pattern.compile("<blockquote.+</blockquote>");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String re = matcher.group();
            text = text.replace(re, "");
        }
        return text;
    }
}
