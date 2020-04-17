package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartDto {
    private Long id;

    private List<CartItemDto> cartItems = new ArrayList<>(0);

    public void addCartItem(BookDto bookDTO) {
        CartItemDto newCartItem = new CartItemDto(bookDTO, 1);
        if (cartItems.contains(newCartItem)) {
            for (CartItemDto oldCartItem : cartItems) {
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
        for (CartItemDto cartItemDTO : cartItems) {
            if (cartItemDTO.getBook().getId() == id) {
                cartItems.remove(cartItemDTO);
                return cartItemDTO.getId();
            }
        }
        return null;
    }


    public void updateCartItem(Long id, Integer quantity) {
        for (CartItemDto cartItemDTO : cartItems) {
            if (cartItemDTO.getBook().getId() == id) {
                cartItemDTO.setQuantity(quantity);
            }
        }
    }

    public void mergeCartItem(Long id, Integer quantity) {
        for (CartItemDto cartItemDTO : cartItems) {
            if (cartItemDTO.getBook().getId() == id) {
                cartItemDTO.setQuantity(quantity + cartItemDTO.getQuantity());
            }
        }
    }

    public void mergeCarts(ShoppingCartDto shoppingCartDTO) {
        if (cartItems.size() != 0) {
            for (CartItemDto newCartItemDto : shoppingCartDTO.cartItems) {
                if (cartItems.contains(newCartItemDto)) {
                    mergeCartItem(newCartItemDto.getBook().getId(), newCartItemDto.getQuantity());
                } else {
                    cartItems.add(newCartItemDto);
                }
            }
        } else cartItems.addAll(shoppingCartDTO.getCartItems());
    }

    public long getTotalCostItems() {
        long cost = 0;
        for (CartItemDto cartItemDTO : cartItems) {
            cost += cartItemDTO.getBook().getPrice() * cartItemDTO.getQuantity();
        }
        return cost;
    }
}
