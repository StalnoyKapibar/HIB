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
                    oldCartItem.setQuantity(newCartItem.getQuantity() + 1);
                    break;
                }
            }
        } else {
            cartItems.add(newCartItem);
        }
    }

    public void deleteCartItem(Long id) {
        cartItems.removeIf(cartItemDTO -> cartItemDTO.getBook().getId() == id);
    }

    public void updateCartItem(Long id, Integer quantity) {
        for (CartItemDTO cartItemDTO : cartItems) {
            if (cartItemDTO.getBook().getId() == id) {
                cartItemDTO.setQuantity(quantity);
            }
        }
    }
    public void mergeCarts(ShoppingCartDTO shoppingCartDTO){
        for (CartItemDTO cartItemDTO:shoppingCartDTO.cartItems){
            addCartItem(cartItemDTO.getBook());
        }
    }
}
