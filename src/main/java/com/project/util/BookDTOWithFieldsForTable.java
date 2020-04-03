package com.project.util;

import com.project.model.BookDTO;
import com.project.model.LocaleString;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("BookDTOWithFieldsForTable")
public class BookDTOWithFieldsForTable {
    private Class localeStringClass = BookDTO.class;
    private List<String> fields = new ArrayList<>();

    public BookDTOWithFieldsForTable() {
        Arrays.stream(localeStringClass.getDeclaredFields())
                .filter(n -> n.getType().equals(LocaleString.class))
                .forEach(n -> fields.add(n.getName()));
    }

    public List<String> getFields() {
        return fields;
    }
}
