package com.project.dao;

import com.project.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountDao extends CrudRepository<UserAccount, Long> {

    @Override
    <S extends UserAccount> S save(S entity);

    @Override
    boolean existsById(Long aLong);

    @Override
    Optional<UserAccount> findById(Long aLong);

    Optional<UserAccount> findByLogin(String login);

}
