package com.project.dao.abstraction;

import com.project.model.ShoppingCartDTO;

import java.util.List;

public interface ShoppingCartDao {
    ShoppingCartDTO getCartById(Long id);

    void updateCart(ShoppingCartDTO cart);

    List getMaxIdCartItem();
}
