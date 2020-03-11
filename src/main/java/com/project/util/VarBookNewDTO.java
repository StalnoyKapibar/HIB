package com.project.util;

import com.project.model.BookDTO;
import com.project.model.BookNewDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VarBookNewDTO {
    private Class localeStringClass = BookNewDTO.class;
    private List<String> fields = new ArrayList<>();

    public VarBookNewDTO() {
        Arrays.stream(localeStringClass.getDeclaredFields())
                .filter(n -> !(n.getName().equals("coverImage")))
                .filter(n -> !(n.getName().equals("imageList")))
                .forEach(n -> fields.add(n.getName()));
    }

    public List<String> getFields() {
        return fields;
    }
}
