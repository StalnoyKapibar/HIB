package com.project.service;

import com.project.controller.controller.DocHTMLController;
import com.project.dao.UserAccountDao;
import com.project.dao.abstraction.UserRoleDao;
import com.project.mail.MailService;
import com.project.model.RegistrationUserDTO;
import com.project.model.Role;
import com.project.model.ShoppingCart;
import com.project.model.UserAccount;
import com.project.service.abstraction.SendEmailService;
import com.project.service.abstraction.UserAccountService;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import sun.plugin.dom.html.HTMLDocument;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    UserAccountDao userAccountDao;

    UserRoleDao userRoleDao;

    PasswordEncoder encoder;

    HttpSession httpSession;

    SendEmailService sendEmailService;

    Environment environment;

    @Override
    public UserAccount findUserByToConfirmEmail(String token) {
        try {
            return userAccountDao.findUserByTokenToConfirmEmail(token);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean emailExist(String email) {
        try {
            userAccountDao.findByEmail(email).isPresent();
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public UserAccount save(RegistrationUserDTO user) throws ConstraintViolationException {
        UserAccount userAccount = UserAccount.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .regDate(Instant.now().getEpochSecond())
                .provider("local")
                .locale(httpSession.getAttribute("LANG").toString())
                .isEnabled(true)
                .cart(new ShoppingCart())
                .tokenToConfirmEmail(UUID.randomUUID().toString())
                .roles(new Role(1L, "ROLE_USER"))
                .build();

        try {
            if (userAccount != null) {
                sendEmailService.confirmAccount(userAccount);
                sendEmailService.confirmAccount1ClickReg(userAccount, user.getPassword(), user.getLogin());
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return userAccountDao.save(userAccount);
    }


    @Override
    public void setLocaleAndAuthDate(String email, String locale, long lastAuthDate) {
        userAccountDao.setLocaleAndAuthDate(email, locale, lastAuthDate);
    }

    @Override
    public UserAccount save(UserAccount user) {
        return userAccountDao.save(user);
    }

    @Override
    public UserAccount getUserById(Long id) {
        return userAccountDao.findById(id);
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        userAccountDao.update(userAccount);
        return userAccount;
    }


    public UserAccount findByLogin(String login) {
        return userAccountDao.findByLogin(login).get();
    }

    @Override
    public UserAccount findByEmail(String email){
        try {
            return userAccountDao.findByEmail(email).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
