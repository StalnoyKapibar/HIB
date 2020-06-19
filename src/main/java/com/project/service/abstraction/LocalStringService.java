package com.project.service.abstraction;

import com.project.model.Category;
import com.project.model.LocaleString;

import java.util.List;

public interface LocalStringService {
    void addLocalString(LocaleString localeString);

    List<LocaleString> getLocalString(String name, List<Category> categories);

}
