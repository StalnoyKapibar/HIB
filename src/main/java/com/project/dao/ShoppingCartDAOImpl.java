package com.project.dao;

import com.project.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class ShoppingCartDAOImpl implements ShoppingCartDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private BookDAOImpl bookDAO;


    @Override
    public ShoppingCartDTO getCartById(Long id) {
        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, id);
        ShoppingCartDTO cartDTO = new ShoppingCartDTO();
        cartDTO.setId(shoppingCart.getId());
        cartDTO.setId(shoppingCart.getId());
        List<CartItemDTO> itemDTO = new ArrayList<>();
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            itemDTO.add(new CartItemDTO(cartItem.getId(), bookDAO.getBookDTOById(cartItem.getBook().getId()), cartItem.getQuantity()));
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
                items.add(new CartItem(cartItemDTO.getId(), new Book(cartItemDTO.getBook().getId(),
                        cartItemDTO.getBook().getName(),
                        cartItemDTO.getBook().getAuthor(),
                        cartItemDTO.getBook().getDesc(),
                        cartItemDTO.getBook().getEdition(),
                        cartItemDTO.getBook().getYearOfEdition(),
                        cartItemDTO.getBook().getPages(),
                        cartItemDTO.getBook().getPrice(),
                        cartItemDTO.getBook().getCoverImage(),
                        cartItemDTO.getBook().getOriginalLanguage(),
                        cartItemDTO.getBook().getImageList(),
                        0L, cartItemDTO.getBook().getStatusInStock()), cartItemDTO.getQuantity()));
            } else {
                items.add(new CartItem(new Book(cartItemDTO.getBook().getId(),
                        cartItemDTO.getBook().getName(),
                        cartItemDTO.getBook().getAuthor(),
                        cartItemDTO.getBook().getDesc(),
                        cartItemDTO.getBook().getEdition(),
                        cartItemDTO.getBook().getYearOfEdition(),
                        cartItemDTO.getBook().getPages(),
                        cartItemDTO.getBook().getPrice(),
                        cartItemDTO.getBook().getCoverImage(),
                        cartItemDTO.getBook().getOriginalLanguage(),
                        cartItemDTO.getBook().getImageList(),
                        0L, cartItemDTO.getBook().getStatusInStock()), cartItemDTO.getQuantity()));
            }
        }
        updateCart.setCartItems(items);
        entityManager.merge(updateCart);
    }
}
