package com.project.service;

import com.project.model.ShoppingCartDTO;

import javax.servlet.http.HttpServletRequest;

public interface ShoppingCartService {
    ShoppingCartDTO getCartById(Long id);

    void updateCart(ShoppingCartDTO cart);

    void deletCartItem(Long id);

    void mergeCarts(HttpServletRequest request, Long id);
}
