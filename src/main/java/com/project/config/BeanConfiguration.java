package com.project.config;

import com.project.util.LocaleHolder;
import com.project.util.BookDTOWithFieldsForTable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean("localeHolder")
    public LocaleHolder getLocaleHolder() {
        return new LocaleHolder();
    }
}
