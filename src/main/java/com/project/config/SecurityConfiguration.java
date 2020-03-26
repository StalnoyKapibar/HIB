package com.project.config;

import com.project.filter.FilterSession;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private FilterSession filterSession;

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(filterSession, BasicAuthenticationFilter.class);

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/admin", "/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/home")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/home?logout")
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
}
