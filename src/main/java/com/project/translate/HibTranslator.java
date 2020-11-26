package com.project.translate;

public interface HibTranslator {
    public String translate(String text, String lang);

    public String translate(String langFrom, String langTo, String text);
}
