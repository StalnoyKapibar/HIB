package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartDTO {
    private Long id;

    private List<CartItemDTO> cartItems = new ArrayList<>(0);

    public void addCartItem(BookDTO bookDTO) {
        CartItemDTO newCartItem = new CartItemDTO(bookDTO, 1);
        if (cartItems.contains(newCartItem)) {
            for (CartItemDTO oldCartItem : cartItems) {
                if (oldCartItem.equals(newCartItem)) {
                    oldCartItem.setQuantity(oldCartItem.getQuantity() + 1);
                    break;
                }
            }
        } else {
            cartItems.add(newCartItem);
        }
    }

    public Long deleteCartItem(Long id) {
        for (CartItemDTO cartItemDTO : cartItems) {
            if (cartItemDTO.getBook().getId() == id) {
                cartItems.remove(cartItemDTO);
                return cartItemDTO.getId();
            }
        }
        return null;
    }


    public void updateCartItem(Long id, Integer quantity) {
        for (CartItemDTO cartItemDTO : cartItems) {
            if (cartItemDTO.getBook().getId() == id) {
                cartItemDTO.setQuantity(quantity);
            }
        }
    }

    public void mergeCartItem(Long id, Integer quantity) {
        for (CartItemDTO cartItemDTO : cartItems) {
            if (cartItemDTO.getBook().getId() == id) {
                cartItemDTO.setQuantity(quantity + cartItemDTO.getQuantity());
            }
        }
    }

    public void mergeCarts(ShoppingCartDTO shoppingCartDTO) {
        if (cartItems.size() != 0) {
            for (CartItemDTO newcartItemDTO : shoppingCartDTO.cartItems) {
                if (cartItems.contains(newcartItemDTO)) {
                    mergeCartItem(newcartItemDTO.getBook().getId(), newcartItemDTO.getQuantity());
                } else {
                    cartItems.add(newcartItemDTO);
                }
            }
        } else cartItems.addAll(shoppingCartDTO.getCartItems());
    }
}
