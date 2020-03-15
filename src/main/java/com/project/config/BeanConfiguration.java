package com.project.config;

import com.project.config.initializer.TestDataInit;
import com.project.util.LocaleHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class BeanConfiguration {

    @Bean("localeHolder")
    public LocaleHolder getLocaleHolder() {
        return new LocaleHolder();
    }


   /* @Bean(initMethod = "init")
    @PostConstruct
    public TestDataInit initTestData() {
        return new TestDataInit();
    }*/
}
