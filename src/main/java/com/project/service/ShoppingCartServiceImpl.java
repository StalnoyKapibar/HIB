package com.project.service;

import com.project.dao.abstraction.CartItemDao;
import com.project.dao.abstraction.ShoppingCartDao;
import com.project.model.ShoppingCartDTO;
import com.project.service.abstraction.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartDao cartDAO;

    private CartItemDao cartItemDAO;


    @Override
    public ShoppingCartDTO getCartById(Long id) {
        return cartDAO.getCartById(id);
    }

    @Override
    public void updateCart(ShoppingCartDTO cart) {
        cartDAO.updateCart(cart);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemDAO.deleteById(id);
    }

    public void deleteBookFromShopCartCartItem(Long shopCartId, Long cartItemDtoId) {
        cartDAO.deleteBookFromShopCartCartItem(shopCartId, cartItemDtoId);
    }

    @Override
    public void mergeCarts(HttpServletRequest request, Long id) {
        ShoppingCartDTO cart = (ShoppingCartDTO) request.getSession().getAttribute("shoppingcart");
        if (cart != null) {
            ShoppingCartDTO mainCart = cartDAO.getCartById(id);
            mainCart.mergeCarts(cart);
            cartDAO.updateCart(mainCart);
            request.getSession().removeAttribute("shoppingcart");
            request.getSession().setAttribute("cartId", id);
        }
    }
    @Override
    public List getMaxIdCartItem(){
       return cartDAO.getMaxIdCartItem();
    }
}
