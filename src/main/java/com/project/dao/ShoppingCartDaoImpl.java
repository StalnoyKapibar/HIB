package com.project.dao;

import com.project.dao.abstraction.ShoppingCartDao;
import com.project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShoppingCartDaoImpl extends AbstractDao<Long, ShoppingCart> implements ShoppingCartDao {
    ShoppingCartDaoImpl() {
        super(ShoppingCart.class);
    }

    @Autowired
    private BookDaoImpl bookDAO;

    @Override
    public ShoppingCartDTO getCartById(Long id) {
        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, id);
        ShoppingCartDTO cartDTO = new ShoppingCartDTO();
        cartDTO.setId(shoppingCart.getId());
        cartDTO.setId(shoppingCart.getId());
        List<CartItemDTO> itemDTO = new ArrayList<>();
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            itemDTO.add(new CartItemDTO(cartItem.getId(), bookDAO.findById(cartItem.getBook().getId())));
        }
        cartDTO.setCartItems(itemDTO);
        return cartDTO;
    }

    @Override
    public void updateCart(ShoppingCartDTO cart) {
        ShoppingCart updateCart = new ShoppingCart();
        updateCart.setId(cart.getId());
        List<CartItem> items = new ArrayList<>();
        for (CartItemDTO cartItemDTO : cart.getCartItems()) {
            if (cartItemDTO.getId() != null) {
                items.add(new CartItem(cartItemDTO.getId(), bookDAO.findById(cartItemDTO.getBook().getId())));
            } else {
                items.add(new CartItem(bookDAO.findById(cartItemDTO.getBook().getId())));
            }
        }
        updateCart.setCartItems(items);
        entityManager.merge(updateCart);
    }
    @Override
    public List getMaxIdCartItem(){
        return entityManager.createQuery("select (cartitem.id) from CartItem cartitem").getResultList();

    }
}
