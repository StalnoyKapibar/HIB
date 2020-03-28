package com.project.dao;

import com.project.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountDao extends CrudRepository<UserAccount, Long> {
    Optional<UserAccount> findByLogin(String login);
    Optional<UserAccount> findByEmail(String email);

    UserAccount findByLoginOrEmail(String login, String email);
}
