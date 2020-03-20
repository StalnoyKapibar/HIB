package com.project.service;

import com.project.dao.UserAccountDao;
import com.project.dao.UserRoleDao;
import com.project.model.RegistrationUserDTO;
import com.project.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.SetUtils;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    UserAccountDao userAccountDao;

    UserRoleDao userRoleDao;

    PasswordEncoder encoder;

    @Override
    public UserAccount save(RegistrationUserDTO user) throws ConstraintViolationException {
        UserAccount userAccount = UserAccount.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .roles(SetUtils.singletonSet(userRoleDao.findByName("ROLE_USER").get()))
                .build();
        return userAccountDao.save(userAccount);
    }

    @Override
    public Optional<UserAccount> findByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public UserAccount save(UserAccount user) {
        return new UserAccount();
    }
}
