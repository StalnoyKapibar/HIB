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
public class OrderDto {
    private Long id;
    private String data;
    private List<CartItemDto> items;
    private AddressDto address;
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
        for (CartItemDto cartItemDTO : items) {
            BookDto bookDTO = cartItemDTO.getBook();
            Book book = Book.builder()
                    .id(bookDTO.getId())
                    .nameLocale(bookDTO.getName())
                    .authorLocale(bookDTO.getAuthor())
                    .desc(bookDTO.getDesc())
                    .edition(bookDTO.getEdition())
                    .yearOfEdition(bookDTO.getYearOfEdition())
                    .pages(bookDTO.getPages())
                    .price(bookDTO.getPrice())
                    .coverImage(bookDTO.getCoverImage())
                    .originalLanguage(bookDTO.getOriginalLanguage())
                    .listImage(bookDTO.getImageList())
                    .build();
            cartItems.add(new CartItem(cartItemDTO.getId(), book, cartItemDTO.getQuantity()));
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
