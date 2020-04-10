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

    public boolean addCartItem(BookDTO bookDTO) {
        CartItemDTO newCartItem = new CartItemDTO(bookDTO, 1);
        if (cartItems.contains(newCartItem)) {
            for (CartItemDTO oldCartItem : cartItems) {
                if (oldCartItem.equals(newCartItem)) {
                    //    oldCartItem.setQuantity(oldCartItem.getQuantity() + 1);
                    return false;
                }
            }
        } else {
            cartItems.add(newCartItem);
        }
        return true;
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
            for (CartItemDTO newCartItemDTO : shoppingCartDTO.cartItems) {
                if (cartItems.contains(newCartItemDTO)) {
                    //  mergeCartItem(newCartItemDTO.getBook().getId(), newCartItemDTO.getQuantity());
                } else {
                    cartItems.add(newCartItemDTO);
                }
            }
        } else cartItems.addAll(shoppingCartDTO.getCartItems());
    }

    public long getTotalCostItems() {
        long cost = 0;
        for (CartItemDTO cartItemDTO : cartItems) {
            cost += cartItemDTO.getBook().getPrice() * cartItemDTO.getQuantity();
        }
        return cost;
    }
}
