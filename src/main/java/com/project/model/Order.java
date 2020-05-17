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
    private String trackingNumber;
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;
    @OneToOne(cascade = CascadeType.ALL)
    private ContactsOfOrder contacts;
    private String comment;

    public OrderDTO getOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setData(data);
        orderDTO.setItemsCost(itemsCost);
        orderDTO.setTrackingNumber(trackingNumber);
        orderDTO.setStatus(status);
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        for (CartItem cartItem : items) {
            Book book = cartItem.getBook();
            cartItemDTOS.add(new CartItemDTO(cartItem.getId(), book));
        }
        orderDTO.setItems(cartItemDTOS);

        orderDTO.setUserDTO(new UserDTO(userAccount.getFirstName(),
                userAccount.getLastName(),
                userAccount.getEmail()));
        orderDTO.setComment(comment);
        orderDTO.setContacts(new ContactsOfOrderDTO(contacts));
        return orderDTO;
    }

    public OrderDTO getOrderDTOForAdmin() {
        OrderDTO orderDTO = getOrderDTO();
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        for (CartItem cartItem : items) {
            Book book = new Book(cartItem.getBook().getId(),
                    cartItem.getBook().getOriginalLanguage(),
                    cartItem.getBook().getPrice(),
                    cartItem.getBook().getCoverImage());
            book.setId(cartItem.getBook().getId());

            cartItemDTOS.add(new CartItemDTO(cartItem.getId(), book));
        }
        orderDTO.setItems(cartItemDTOS);

        return orderDTO;
    }
}
