package com.project.util;

import com.project.model.LocaleString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocaleHolder {
    private List<String> fields = new ArrayList<>();

    public LocaleHolder() {
        Arrays.stream(LocaleString.class.getDeclaredFields())
                .filter(n -> n.getType().equals(String.class) & (n.getName().length() == 2))
                .forEach(n -> fields.add(n.getName()));
    }

    public List<String> getFields() {
        return fields;
    }

}
