package com.project.dao;

import com.project.model.UserAccount;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserAccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<UserAccount> findByLogin(String login) {
        String sql= "SELECT * FROM users WHERE login =:login";
        try {
            UserAccount userAccount =  (UserAccount) entityManager.createNativeQuery(sql, UserAccount.class)
                    .setParameter("login", login).getSingleResult();
            return Optional.ofNullable(userAccount);
        } catch (NoResultException e) {
            throw new UsernameNotFoundException(login);
        }
    }
    public Optional<UserAccount> findByEmail(String email) throws EmptyResultDataAccessException {
        String sql= "SELECT * FROM users WHERE email =:email";
            UserAccount userAccount =  (UserAccount) entityManager.createNativeQuery(sql, UserAccount.class)
                    .setParameter("email", email).getSingleResult();
            return Optional.ofNullable(userAccount);
    }

    public UserAccount findUserByTokenToConfirmEmail(String token) throws EmptyResultDataAccessException {
        String sql= "SELECT * FROM users WHERE token_to_confirm_email =:token";
        return (UserAccount) entityManager.createNativeQuery(sql, UserAccount.class)
                .setParameter("token", token).getSingleResult();
    }

    public void setLocaleAndAuthDate(String email, String locale, long lastAuthDate) {
        String sql= "UPDATE users SET last_auth_date =:lastAuthDate, locale =:locale WHERE email =:email";
        entityManager.createNativeQuery(sql)
                .setParameter("locale", locale)
                .setParameter("lastAuthDate", lastAuthDate)
                .setParameter("email", email).executeUpdate();
    }

    public UserAccount save(UserAccount userAccount) {
        entityManager.persist(userAccount);
        return userAccount;
    }
}
