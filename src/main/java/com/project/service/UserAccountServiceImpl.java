package com.project.service;

import com.project.dao.UserAccountDAO;
import com.project.dao.UserRoleDao;
import com.project.mail.MailService;
import com.project.model.RegistrationUserDTO;
import com.project.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    UserAccountDAO userAccountDao;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    HttpSession httpSession;
    @Autowired
    MailService mailService;

    @Value("${spring.mail.username}")
    String senderFromProperty;

    public UserAccount findUserByToConfirmEmail(String token) {
        try {
            return userAccountDao.findUserByTokenToConfirmEmail(token);
        } catch (EmptyResultDataAccessException e) {
            return null;
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
                .isEnabled(false)
                .tokenToConfirmEmail(UUID.randomUUID().toString())
                .authorities(userRoleDao.findByRoleName("ROLE_USER"))
                .build();

        sendEmailToConfirmAccount(userAccount);
        return userAccountDao.save(userAccount);
    }

    public void sendEmailToConfirmAccount(UserAccount user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Привет");
        mailMessage.setFrom(senderFromProperty);
        mailMessage.setText("Привет "
                + "http://localhost:8080/confirmEmail?token="
                + user.getTokenToConfirmEmail());
        mailService.sendEmail(mailMessage);
    }

    public void setLocaleAndAuthDate(String email, String locale, long lastAuthDate) {
        userAccountDao.setLocaleAndAuthDate(email, locale, lastAuthDate);
    }

    @Override
    public UserAccount save(UserAccount user) {
        return userAccountDao.save(user);
    }
}
