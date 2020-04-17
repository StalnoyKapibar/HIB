package com.project.service.abstraction;

import com.project.model.ShoppingCartDto;

import javax.servlet.http.HttpServletRequest;

public interface ShoppingCartService {
    ShoppingCartDto getCartById(Long id);

    void updateCart(ShoppingCartDto cart);

    void deleteCartItem(Long id);

    void mergeCarts(HttpServletRequest request, Long id);
}
