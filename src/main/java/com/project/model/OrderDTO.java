package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private Long id;
    private long date;
    private List<CartItemDTO> items;
    private Integer itemsCost;
    private Integer shippingCost;
    private String trackingNumber;
    private Status status;
    private UserAccount userAccount;
    private UserDTO userDTO;
    private ContactsOfOrderDTO contacts;
    private String comment;
    private String data;

    @Transient
    public Order getOder() {
        Order order = new Order();
        if (id != null) {
            order.setId(id);
        }
        order.setData(date);
        order.setItemsCost(itemsCost);
        order.setTrackingNumber(trackingNumber);
        order.setStatus(status);
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemDTO cartItemDTO : items) {
            Book book = cartItemDTO.getBook();
            cartItems.add(new CartItem(cartItemDTO.getId(), book));
        }
        order.setItems(cartItems);
        order.setContacts(new ContactsOfOrder(contacts));
        order.setComment(comment);
        order.setUserAccount(userAccount);
        return order;
    }
}
