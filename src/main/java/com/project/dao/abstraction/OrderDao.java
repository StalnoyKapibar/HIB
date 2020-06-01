package com.project.dao.abstraction;

import com.project.model.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Long, Order> {

    List<Order> getOrdersByUserId(Long id);

    List<Order> getOrdersByStatus(String status);

    int getCountOfOrders(long lastAuthDate);
}
