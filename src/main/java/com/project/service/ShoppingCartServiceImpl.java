package com.project.service;

import com.project.dao.CartItemDAO;
import com.project.dao.ShoppingCartDAO;
import com.project.model.ShoppingCartDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartDAO cartDAO;
    private CartItemDAO cartItemDAO;


    @Override
    public ShoppingCartDTO getCartById(Long id) {
        return cartDAO.getCartById(id);
    }

    @Override
    public void updateCart(ShoppingCartDTO cart) {
        cartDAO.updateCart(cart);
    }

    @Override
    public void deletCartItem(Long id) {
        cartItemDAO.deleteCartItem(id);
    }
}
