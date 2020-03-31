package com.project.service;

import com.project.model.RegistrationUserDTO;
import com.project.model.UserAccount;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Optional;

public interface UserAccountService {

    UserAccount findUserByToConfirmEmail(String token);

    UserAccount save(RegistrationUserDTO user) throws ConstraintViolationException;

    void sendEmailToConfirmAccount(UserAccount user);

    void setLocaleAndAuthDate(String email, String locale, long lastAuthDate);

    UserAccount save(UserAccount user);
}