package com.project.util;


import com.project.model.OtherLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtherLangFields {
    private Class otherLanguageStringClass = OtherLanguage.class;
    private List<String> fields = new ArrayList<>();

    public OtherLangFields() {
        Arrays.stream(otherLanguageStringClass.getDeclaredFields())
                .filter(n -> n.getType().equals(String.class) )
                .forEach(n -> fields.add(n.getName()));
    }

    public List<String> getFields() {
        return fields;
    }
}
