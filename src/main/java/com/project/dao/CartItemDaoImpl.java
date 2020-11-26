package com.project.dao;

import com.project.dao.abstraction.CartItemDao;
import com.project.model.CartItem;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemDaoImpl extends AbstractDao<Long, CartItem> implements CartItemDao {
    CartItemDaoImpl() {
        super(CartItem.class);
    }
}
