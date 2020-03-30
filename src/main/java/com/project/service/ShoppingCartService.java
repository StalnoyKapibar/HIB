package com.project.service;

import com.project.model.ShoppingCartDTO;

public interface ShoppingCartService {
    ShoppingCartDTO getCartById(Long id);

    void updateCart(ShoppingCartDTO cart);
}
