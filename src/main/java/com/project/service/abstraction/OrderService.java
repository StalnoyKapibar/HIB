package com.project.service.abstraction;

import com.project.model.Order;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);

    void updateOrder(Order order);

    Order getOrderById(Long id);

    void deleteOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(Long id);

    List<Order> getOdersByStatus(String status);

    void completeOrder(Long id);

    int getCountOfOrders(long lastAuthDate);
}
