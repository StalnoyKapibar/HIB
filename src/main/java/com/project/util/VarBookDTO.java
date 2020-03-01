package com.project.util;

import com.project.model.BookDTO;
import com.project.model.LocaleString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VarBookDTO {
    private Class localeStringClass = BookDTO.class;
    private List<String> fields = new ArrayList<>();

    public VarBookDTO() {
        Arrays.stream(localeStringClass.getDeclaredFields())
                .forEach(n -> fields.add(n.getName()));
    }

    public List<String> getFields() {
        return fields;
    }

}
