package com.project.config.social;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import javax.sql.DataSource;


@EnableSocial
@Configuration
@RequiredArgsConstructor
public class SocialConfiguration implements SocialConfigurer {
    @Autowired
    private DataSource dataSource;

    private ConnectionSignUp connectionSignUp;


    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionCfg, Environment env) {
        //Коннекшнфакторис
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator locator) {
        JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(dataSource, locator, null);
        connectionRepository.setConnectionSignUp(connectionSignUp);
        return connectionRepository;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator locator, UsersConnectionRepository repository) {
        return new ProviderSignInUtils(locator, repository);
    }

}