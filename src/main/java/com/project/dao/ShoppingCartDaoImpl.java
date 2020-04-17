package com.project.dao;

import com.project.dao.abstraction.ShoppingCartDao;
import com.project.model.CartItem;
import com.project.model.CartItemDto;
import com.project.model.ShoppingCart;
import com.project.model.ShoppingCartDto;
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
    public ShoppingCartDto getCartById(Long id) {
        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, id);
        ShoppingCartDto cartDTO = new ShoppingCartDto();
        cartDTO.setId(shoppingCart.getId());
        cartDTO.setId(shoppingCart.getId());
        List<CartItemDto> itemDTO = new ArrayList<>();
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            itemDTO.add(new CartItemDto(cartItem.getId(), bookDAO.getBookDTOById(cartItem.getBook().getId()), cartItem.getQuantity()));
        }
        cartDTO.setCartItems(itemDTO);
        return cartDTO;
    }

    @Override
    public void updateCart(ShoppingCartDto cart) {
        ShoppingCart updateCart = new ShoppingCart();
        updateCart.setId(cart.getId());
        List<CartItem> items = new ArrayList<>();
        for (CartItemDto cartItemDTO : cart.getCartItems()) {
            if (cartItemDTO.getId() != null) {
                items.add(new CartItem(cartItemDTO.getId(), bookDAO.findById(cartItemDTO.getBook().getId()), cartItemDTO.getQuantity()));
            } else {
                items.add(new CartItem(bookDAO.findById(cartItemDTO.getBook().getId()), cartItemDTO.getQuantity()));
            }
        }
        updateCart.setCartItems(items);
        entityManager.merge(updateCart);
    }
}
