package com.project.service.abstraction;

import com.project.model.ShoppingCartDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShoppingCartService {
    default ShoppingCartDTO getCartById(Long id) {
        return null;
    }

    void updateCart(ShoppingCartDTO cart);

    void deleteCartItem(Long id);

    void mergeCarts(HttpServletRequest request, Long id);

    List getMaxIdCartItem();

}
