package com.project.service;

import com.project.model.RegistrationUserDTO;
import com.project.model.UserAccount;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Optional;

public interface UserAccountService {

    UserAccount save(UserAccount user);

    UserAccount save(RegistrationUserDTO user) throws ConstraintViolationException;

}
