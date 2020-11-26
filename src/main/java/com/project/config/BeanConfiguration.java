package com.project.config;

import com.project.service.UserDetailServiceImpl;
import com.project.util.LocaleHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

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

    /*@Bean(initMethod = "init")
    @PostConstruct
    public TestCategories initTestCategories() { return new TestCategories();}

    @Bean(initMethod = "init")
    @DependsOn("checkAndCreateImg")
    @PostConstruct
    public TestDataInit initTestData() {
        return new TestDataInit();
    }

    @Bean(initMethod = "init")
    @PostConstruct
    public TestUserAccounts initTestUserAccount(){
        return new TestUserAccounts();
    }

    @Bean(initMethod = "init")
    @PostConstruct
    public ErrorMessageDataInit initMessageData(){
        return new ErrorMessageDataInit();
    }*/

    @Bean("userDetailsService")
    public UserDetailsService getUserDetailsService(){ return new UserDetailServiceImpl(); }

}
