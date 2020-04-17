package com.project.dao.abstraction;

import com.project.model.ShoppingCartDTO;

public interface ShoppingCartDao {
    ShoppingCartDTO getCartById(Long id);

    void updateCart(ShoppingCartDTO cart);
}
