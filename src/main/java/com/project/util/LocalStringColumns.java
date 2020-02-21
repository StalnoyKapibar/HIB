package com.project.util;

import com.project.model.LocaleString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalStringColumns {
    private static LocalStringColumns instance;
    private Class localeStringClass = LocaleString.class;
    private List<String> fields = new ArrayList<>();

    private LocalStringColumns() {
        Arrays.stream(localeStringClass.getDeclaredFields())
                .filter(n -> !n.getType().equals(Long.class))
                .forEach(n -> fields.add(n.getName()));
    }

    public static LocalStringColumns getInstance() {
        if (instance == null) {
            instance = new LocalStringColumns();
        }
        return instance;
    }

    public List<String> getFields() {
        return fields;
    }
}
