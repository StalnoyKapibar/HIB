package com.project.controller.restcontroller.emailUtil.emailParser;

public class DefaultParser implements EmailParser {
    @Override
    public String getMessageTextWithoutRe(String text) {
        return text;
    }
}
