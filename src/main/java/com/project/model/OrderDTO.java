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
    private String data;
    private List<CartItemDTO> items;
    private AddressDTO address;
    private Integer itemsCost;
    private Integer shippingCost;
    private String trackingNumber;
    private String status;
    private UserAccount userAccount;
    @Transient
    public Order getOder() {
        Order order = new Order();
        if (id != null) {
            order.setId(id);
        }
        order.setData(data);
        order.setItemsCost(itemsCost);
        order.setShippingCost(shippingCost);
        order.setTrackingNumber(trackingNumber);
        order.setStatus(status);
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemDTO cartItemDTO : items) {
            Book book = new Book(cartItemDTO.getBook().getId(),
                    cartItemDTO.getBook().getName(),
                    cartItemDTO.getBook().getAuthor(),
                    cartItemDTO.getBook().getCoverImage(),
                    cartItemDTO.getBook().getPrice(),
                    cartItemDTO.getBook().getImageList(), null);
        }
        order.setItems(cartItems);
        order.setAddress(new Address(address.getId(),
                address.getFlat(),
                address.getHouse(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.getLastName(),
                address.getFirstName()));
        order.setUserAccount(userAccount);
        return order;
    }
}
