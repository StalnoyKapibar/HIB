package com.project.service;

import com.project.dao.UserAccountDao;
import com.project.dao.abstraction.UserRoleDao;
import com.project.mail.MailService;
import com.project.model.*;
import com.project.service.abstraction.UserAccountService;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    UserAccountDao userAccountDao;

    UserRoleDao userRoleDao;

    PasswordEncoder encoder;

    HttpSession httpSession;

    MailService mailService;

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
        return userAccountDao.findByEmail(email).isPresent();
    }

    @Override
    public UserAccount save(RegistrationUserDTO user) throws ConstraintViolationException, MailSendException {
        UserAccount userAccount = UserAccount.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .regDate(Instant.now().getEpochSecond())
                .provider("local")
                .locale(httpSession.getAttribute("LANG").toString())
                .cart(new ShoppingCart())
                .tokenToConfirmEmail(UUID.randomUUID().toString())
                .roles(new Role(1L, "ROLE_USER"))
                .build();

        sendEmailToConfirmAccount(userAccount);
        return userAccountDao.save(userAccount);
    }

    @Override
    public UserAccount save1Clickreg(RegistrationUserDTO user) throws ConstraintViolationException {
        UserAccount userAccount = UserAccount.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .regDate(Instant.now().getEpochSecond())
                .provider("local")
                .locale(httpSession.getAttribute("LANG").toString())
                .cart(new ShoppingCart())
                .tokenToConfirmEmail(UUID.randomUUID().toString())
                .roles(new Role(1L, "ROLE_USER"))
                .autoReg(user.isAutoReg())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .build();

        sendEmailToConfirmAccount1ClickReg(userAccount, user.getPassword(), user.getLogin());
        return userAccountDao.save(userAccount);
    }

    @Override
    public void sendEmailToConfirmAccount(UserAccount user) throws MailSendException {
        String senderFromProperty = environment.getProperty("spring.mail.username");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Привет");
        mailMessage.setFrom(senderFromProperty);
        mailMessage.setText("Привет "
//                + "http://localhost:8080/confirmEmail?token="
                + user.getTokenToConfirmEmail());
        mailService.sendEmail(mailMessage);
    }


    public void sendEmailToConfirmAccount1ClickReg(UserAccount user, String password, String login) {
        String senderFromProperty = environment.getProperty("spring.mail.username");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Привет");
        mailMessage.setFrom(senderFromProperty);
        mailMessage.setText("Привет "
//                + "http://localhost:8080/confirmEmail?token="
                + user.getTokenToConfirmEmail() + " Логин: " + login + " Пароль: " + password);
        mailService.sendEmail(mailMessage);
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


    public UserAccount findByLogin(String login) throws UsernameNotFoundException, NoResultException {
        return userAccountDao.findByLogin(login).get();
    }

}
