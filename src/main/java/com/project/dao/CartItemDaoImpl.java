package com.project.dao;

import com.project.dao.abstraction.CartItemDao;
import com.project.model.CartItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CartItemDaoImpl extends AbstractDao<Long, CartItem> implements CartItemDao {
    CartItemDaoImpl() {
        super(CartItem.class);
    }
}
