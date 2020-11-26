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

    public void addCartItem(Book book) {
        for (CartItemDTO cartItem : cartItems) {
            if (cartItem.getBook().getId().equals(book.getId())){
                return;
            }
        }
        cartItems.add(new CartItemDTO(book));
    }

    public Long deleteCartItem(Long id) {
        Long deletedID = null;
        if (cartItems.size() != 0) {
            for (int i = 0; i < cartItems.size(); i++) {
                if (cartItems.get(i).getBook().getId() == id) {
                    deletedID = cartItems.get(i).getId();
                    cartItems.remove(cartItems.get(i));
                }
            }
        }
        return deletedID;
    }

    public void mergeCarts(ShoppingCartDTO shoppingCartDTO) {
        if (cartItems.size() != 0) {
            for (CartItemDTO newCartItemDTO : shoppingCartDTO.cartItems) {
                if (!cartItems.contains(newCartItemDTO)) {
                    cartItems.add(newCartItemDTO);
                }
            }
        } else cartItems.addAll(shoppingCartDTO.getCartItems());
    }

    public long getTotalCostItems() {
        long cost = 0;
        for (CartItemDTO cartItemDTO : cartItems) {
            cost += cartItemDTO.getBook().getPrice();
        }
        return cost;
    }
}
