package com.project.service.abstraction;

import com.project.model.Order;
import com.project.model.Status;

import java.util.List;

public interface OrderService {
    void addOrder(Order order, String url);

    void updateOrder(Order order);

    Order getOrderById(Long id);

    void deleteOrder(Long id);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(Long id);

    void completeOrder(Long id);

    List<Order> getOrderByEmailByStatus(Status status, String email);

    int getCountOfOrders(long lastAuthDate);

    void unCompleteOrder(Long id);

    void processOrder(Long id);

    Long[] getAmountOfOrders(String email);

    List<Order> findOrderByBookId(Long bookId);
}
