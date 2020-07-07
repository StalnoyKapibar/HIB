package com.project.controller.restcontroller.emailUtil.emailParser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YandexRuParser implements EmailParser {
    @Override
    public String getMessageTextWithoutRe(String text) {
        Pattern pattern = Pattern.compile("<div>[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}.+</blockquote>");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String re = matcher.group();
            text = text.replace(re, "");
        }
        return text;
    }
}
