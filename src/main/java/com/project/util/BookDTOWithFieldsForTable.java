package com.project.util;

import com.project.model.BookDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDTOWithFieldsForTable {
    private Class localeStringClass = BookDTO.class;
    private List<String> fields = new ArrayList<>();

    public BookDTOWithFieldsForTable() {
        Arrays.stream(localeStringClass.getDeclaredFields())
                .filter(n -> !(n.getName().equals("coverImage")))
                .filter(n -> !(n.getName().equals("imageList")))
                .forEach(n -> fields.add(n.getName()));
    }

    public List<String> getFields() {
        return fields;
    }
}
