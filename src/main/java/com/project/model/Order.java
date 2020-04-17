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

    public OrderDto getOrderDTO() {
        OrderDto orderDTO = new OrderDto();
        orderDTO.setId(id);
        orderDTO.setData(data);
        orderDTO.setItemsCost(itemsCost);
        orderDTO.setShippingCost(shippingCost);
        orderDTO.setTrackingNumber(trackingNumber);
        orderDTO.setStatus(status);
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItem cartItem : items) {
            Book book = cartItem.getBook();
            BookDto bookDTO =  BookDto.builder()
                    .id(book.getId())
                    .name(book.getNameLocale())
                    .author(book.getAuthorLocale())
                    .desc(book.getDesc())
                    .edition(book.getEdition())
                    .yearOfEdition(book.getYearOfEdition())
                    .pages(book.getPages())
                    .price(book.getPrice())
                    .coverImage(book.getCoverImage())
                    .originalLanguage(book.getOriginalLanguage())
                    .imageList(book.getListImage())
                    .build();
            cartItemDtos.add(new CartItemDto(cartItem.getId(), bookDTO, cartItem.getQuantity()));
        }
        orderDTO.setItems(cartItemDtos);
        orderDTO.setAddress(new AddressDto(address.getId(),
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
