package com.project.dao;

import com.project.model.ShoppingCartDTO;

public interface ShoppingCartDAO {
    ShoppingCartDTO getCartById(Long id);

    void updateCart(ShoppingCartDTO cart);
}
