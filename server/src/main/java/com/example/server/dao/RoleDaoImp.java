package com.example.server.dao;

import com.example.server.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class RoleDaoImp implements RoleDao {

    private RoleDao roleDao;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void add(Role role) {
        entityManager.persist(role);

    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role").getResultList();
    }

    @Override
    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);
    }


}
