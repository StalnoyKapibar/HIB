package com.project.service.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    void addOrder(Order order, String url);

    void addOrder1ClickReg(Order order);

    void updateOrder(Order order);

    Order getOrderById(Long id);

    void deleteOrder(Long id);

    void confirmOrder(Long id);

    String cancelOrder(Long id);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(Long id);

    void completeOrder(Long id);

    List<Order> getOrderByEmailByStatus(Status status, String email);

    int getCountOfOrders();

    void unCompleteOrder(Long id);

    void processOrder(Long id);

    Long[] getAmountOfOrders(String email);

    OrderPageAdminDTO getPageOfOrdersByPageable(Pageable pageable, Status status);

    List<Order> getPageOfOrdersUserByPageable(Pageable pageable, Long id);

    List<Order> findOrderByBookId(Long bookId);

    ResponseEntity createFileAllOrders();

    OrderDTO addOrderReg1Click(ShoppingCartDTO shoppingCart, RegistrationUserDTO user, ContactsOfOrderDTO contacts);

    List<Order> findAllUncompletedOrdersByBookId(Long bookId);
}
