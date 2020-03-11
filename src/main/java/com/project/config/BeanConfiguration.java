package com.project.config;

import com.project.util.LocaleHolder;
import com.project.util.VarBookDTO;
import com.project.util.VarBookNewDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean("localeHolder")
    public LocaleHolder getLocaleHolder() {
        return new LocaleHolder();
    }

    @Bean("varBookDTO")
    public VarBookDTO getVarBookDTO() {
        return new VarBookDTO();
    }

    @Bean("varBookNewDTO")
    public VarBookNewDTO getVarBookNewDTO() {
        return new VarBookNewDTO();
    }


}
