package com.project.dao;

import com.project.model.UserAccount;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAccountDao extends AbstractDao<Long, UserAccount> {
    UserAccountDao() {
        super(UserAccount.class);
    }

    public Optional<UserAccount> findByLogin(String login) throws UsernameNotFoundException, NoResultException {
        String sql = "SELECT * FROM users WHERE login =:login";
        UserAccount userAccount = (UserAccount) entityManager.createNativeQuery(sql, UserAccount.class)
                .setParameter("login", login).getSingleResult();
        return Optional.ofNullable(userAccount);
    }


    public Optional<UserAccount> findByEmail(String email) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM users WHERE email =:email";
        try {
            UserAccount userAccount = (UserAccount) entityManager.createNativeQuery(sql, UserAccount.class)
                    .setParameter("email", email).getSingleResult();
            return Optional.ofNullable(userAccount);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    public UserAccount findUserByTokenToConfirmEmail(String token) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM users WHERE token_to_confirm_email =:token";
        return (UserAccount) entityManager.createNativeQuery(sql, UserAccount.class)
                .setParameter("token", token).getSingleResult();
    }

    @Transactional
    public void setLocaleAndAuthDate(String email, String locale, long lastAuthDate) {
        String sql = "UPDATE users SET last_auth_date =:lastAuthDate, locale =:locale WHERE email =:email";
        entityManager.createNativeQuery(sql)
                .setParameter("locale", locale)
                .setParameter("lastAuthDate", lastAuthDate)
                .setParameter("email", email).executeUpdate();
    }

    public UserAccount save(UserAccount userAccount) {
        entityManager.persist(userAccount);
        return userAccount;
    }

    public List<String> getUsersEmailsByStatus(Boolean isEnabled) {
        return (List<String>) entityManager.createQuery("SELECT email FROM UserAccount ua " +
                "WHERE NOT roles = (SELECT roleId FROM Role r WHERE roleName =:roleName) AND ua.isEnabled =: isEnabled")
                .setParameter("roleName", "ROLE_ADMIN")
                .setParameter("isEnabled", isEnabled)
                .getResultList();
    }
}
