package com.project.config;

import com.project.config.initializer.TestDataInit;
import com.project.util.LocaleHolder;
import com.project.util.BookDTOWithFieldsForTable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class BeanConfiguration {

    @Bean("localeHolder")
    public LocaleHolder getLocaleHolder() {
        return new LocaleHolder();
    }

    @Bean("checkAndCreateImg")
        public void checkAndCreateImg() {
        if (!Files.exists(Paths.get("img"))) {
            try {
                Files.createDirectories(Paths.get("img"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Bean(initMethod = "init")
    @DependsOn("checkAndCreateImg")
    @PostConstruct
    public TestDataInit initTestData() {
        return new TestDataInit();
    }
}
