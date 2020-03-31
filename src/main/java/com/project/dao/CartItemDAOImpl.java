package com.project.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@AllArgsConstructor
public class CartItemDAOImpl implements CartItemDAO {

    private EntityManager entityManager;

    @Override
    public void deleteCartItem(Long id) {
        entityManager.createQuery("DELETE FROM CartItem where id = :id").setParameter("id", id).executeUpdate();
    }
}
