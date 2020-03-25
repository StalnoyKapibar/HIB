package com.project.service;

import com.project.model.RegistrationUserDTO;
import com.project.model.UserAccount;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public interface UserAccountService {

    Optional<UserAccount> findByLogin(String login);

    UserAccount save(UserAccount user);

    UserAccount save(RegistrationUserDTO user) throws ConstraintViolationException;

}
