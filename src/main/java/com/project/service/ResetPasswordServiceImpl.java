package com.project.service;

import com.project.dao.UserAccountDao;
import com.project.mail.MailService;
import com.project.model.UserAccount;
import com.project.model.UserDTOResetPassword;
import com.project.service.abstraction.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@Transactional
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserAccountDao userAccountDAO;

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment environment;

    @Override
    public String sendEmailResetPassword(String email, String url) {
        String reg = "^(.+)@([a-zA-Z]+)\\.([a-zA-Z]+)$";
        if (Pattern.matches(reg, email)) {
            try {
                Optional<UserAccount> userAccount = userAccountDAO.findByEmail(email);
                String token = UUID.randomUUID().toString();
                UserAccount userAccount1 = userAccount.get();
                userAccount1.setTokenToConfirmEmail(token);
                userAccountDAO.save(userAccount1);

                Context context = new Context();
                context.setVariable("url", url);
                context.setVariable("token", token);
                String senderFromProperty = environment.getProperty("spring.mail.username");
                MimeMessage message = mailService.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
                helper.setTo(email);
                helper.setSubject("Reset Password");
                helper.setFrom(senderFromProperty);
                helper.setText(mailService.getTemplate("mailForm/passChange.html", context), true);
                mailService.sendEmail(message);
                return "ok";
            } catch (EmptyResultDataAccessException | MessagingException e) {
                return "noEmail";
            }
        } else {
            return "invalid format email";
        }
    }

    @Override
    public boolean checkTokenResetPassword(String token) {
        try {
            userAccountDAO.findUserByTokenToConfirmEmail(token);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public String saveNewPasswordReset(UserDTOResetPassword userDTOResetPassword) {
        String reg = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";
        if (Pattern.matches(reg, userDTOResetPassword.getPassword()) && (userDTOResetPassword.getPassword().length() >= 8 && userDTOResetPassword.getPassword().length() <= 64)) {
            try {
                UserAccount userAccount = userAccountDAO.findUserByTokenToConfirmEmail(userDTOResetPassword.getToken());
                userAccount.setTokenToConfirmEmail(null);
                userAccount.setPassword(encoder.encode(userDTOResetPassword.getPassword()));
                userAccountDAO.save(userAccount);
                return "ok";
            } catch (EmptyResultDataAccessException e) {
                return "notValid";
            }
        } else {
            return "passError";
        }
    }
}