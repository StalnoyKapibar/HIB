package com.project.dao.abstraction;

import com.project.model.ShoppingCartDto;

public interface ShoppingCartDao {
    ShoppingCartDto getCartById(Long id);

    void updateCart(ShoppingCartDto cart);
}
