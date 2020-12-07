package com.project.config;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;
import java.util.Locale;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/", "classpath:*", "classpath:/static/**"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        registry.addResourceHandler("/images/**").addResourceLocations("file:img/");
    }

    // bean for using Spring Security Dialect with Thymeleaf engine (in html page)
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public ClassLoaderTemplateResolver secondaryTemplateResolver() {
        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("templates/");
        secondaryTemplateResolver.setSuffix(".html");
        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
        secondaryTemplateResolver.setOrder(1);
        secondaryTemplateResolver.setCheckExistence(true);

        return secondaryTemplateResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("LANG");
        return lci;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages");
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        return factory.createMultipartConfig();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("categoryTree", "noParentCategory");
    }

    @Bean(name = "commonsMultipartResolver")
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

//    @Bean
//    public TomcatContextCustomizer sameSiteCookiesConfig() {
//        return context -> {
//            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
//            cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
//            context.setCookieProcessor(cookieProcessor);
//        };
//    }
}