package com.project.config;

import com.project.config.handler.LogoutSuccessHandler;
import com.project.filter.FilterSession;
import com.project.service.OAuthUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private AuthenticationSuccessHandler loginSuccessHandler;

    private AuthenticationFailureHandler loginFailureHandler;

    private AccessDeniedHandler accessDeniedHandler;

    private OAuthUserService oAuthUserService;

    private FilterSession filterSession;

    private LogoutSuccessHandler logoutSuccessHandler;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Фильтры
        http.addFilterBefore(filterSession, BasicAuthenticationFilter.class);

        //Страницы доступные для админа
        http.authorizeRequests().antMatchers("/admin/**", "/admin", "/api/admin/**", "/admin/add")
                .hasRole("ADMIN");
        //Страницы не доступные для админа
        http.authorizeRequests().antMatchers("/shopping-cart").not().hasRole("ADMIN");
        //Страницы доступные для юзеров
        http.authorizeRequests().antMatchers("/user", "/logout", "/cabinet", "/api/user/**", "/profile/**", "/order/**")
                .hasRole("USER");

        http.formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and()
                //OAuth
                .oauth2Login()
                .loginPage("/")
                .successHandler(loginSuccessHandler)
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuthUserService);

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID");

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        http.csrf().disable();
    }
}
