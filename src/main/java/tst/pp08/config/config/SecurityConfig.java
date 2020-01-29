package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.config.handler.LoginSuccessHandler;
import web.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

 /*   @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("ADMIN").password("ADMIN").roles("ADMIN");
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                //указываем логику обработки при логине
                .successHandler(new LoginSuccessHandler())
                // указываем action с формы логина
                .loginProcessingUrl("/login")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("username")
                .passwordParameter("password")
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable();

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()

                .antMatchers("/login").permitAll()


                //страницы аутентификаци доступна всем

                // защищенные URL
                .antMatchers("/user").access("hasAnyAuthority('user', 'admin')")
                .antMatchers("/admin", "/admin/*").access("hasAnyAuthority('admin')").anyRequest().authenticated();
        //   .antMatchers("/user").hasAnyAuthority("user", "admin");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
