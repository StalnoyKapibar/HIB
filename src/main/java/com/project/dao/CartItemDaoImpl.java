package com.project.dao;

import com.project.dao.abstraction.CartItemDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@AllArgsConstructor
public class CartItemDaoImpl implements CartItemDao {

    private EntityManager entityManager;

    @Override
    public void deleteCartItem(Long id) {
        entityManager.createQuery("DELETE FROM CartItem where id = :id").setParameter("id", id).executeUpdate();
    }
}
