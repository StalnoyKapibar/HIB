package com.project.dao;

import com.project.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleDao extends CrudRepository<UserRole, Long> {

    @Override
    Optional<UserRole> findById(Long aLong);

    @Override
    <S extends UserRole> S save(S entity);

    Optional<UserRole> findByName(String name);
}
