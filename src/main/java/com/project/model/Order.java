package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<CartItem> items;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private Integer itemsCost;
    private Integer shippingCost;
    private String trackingNumber;
    private String status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    public OrderDTO getOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setData(data);
        orderDTO.setItemsCost(itemsCost);
        orderDTO.setShippingCost(shippingCost);
        orderDTO.setTrackingNumber(trackingNumber);
        orderDTO.setStatus(status);
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        for (CartItem cartItem : items) {
            BookDTO bookDTO = new BookDTO(cartItem.getBook().getId(),
                    cartItem.getBook().getNameLocale(),
                    cartItem.getBook().getAuthorLocale(),
                    cartItem.getBook().getCoverImage(),
                    cartItem.getBook().getPrice(),
                    cartItem.getBook().getListImage());
            cartItemDTOS.add(new CartItemDTO(cartItem.getId(), bookDTO, cartItem.getQuantity()));
        }
        orderDTO.setItems(cartItemDTOS);
        orderDTO.setAddress(new AddressDTO(address.getId(),
                address.getFlat(),
                address.getHouse(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.getLastName(),
                address.getFirstName()));
        return orderDTO;
    }
}
