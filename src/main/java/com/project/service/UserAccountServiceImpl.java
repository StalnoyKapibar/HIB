package com.project.service;

import com.project.dao.UserAccountDao;
import com.project.dao.UserRoleDao;
import com.project.model.RegistrationUserDTO;
import com.project.model.UserAccount;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.time.Instant;

@Service
@Transactional
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    UserAccountDao userAccountDao;

    UserRoleDao userRoleDao;

    PasswordEncoder encoder;

    HttpSession httpSession;

    @Override
    public UserAccount save(RegistrationUserDTO user) throws ConstraintViolationException {
        UserAccount userAccount = UserAccount.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .regDate(Instant.now().getEpochSecond())
                .provider("local")
                .locale(httpSession.getAttribute("LANG").toString())
                .authorities(userRoleDao.findByRoleName("ROLE_USER"))
                .build();
        return userAccountDao.save(userAccount);
    }


    @Override
    public UserAccount save(UserAccount user) {
        return userAccountDao.save(user);
    }
}
