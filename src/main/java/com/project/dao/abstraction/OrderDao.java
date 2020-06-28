package com.project.dao.abstraction;

import com.project.model.Order;
import com.project.model.Status;

import java.util.List;

public interface OrderDao extends GenericDao<Long, Order> {

    List<Order> getOrdersByUserId(Long id);

    List<Order> getOrdersByStatus(String status);

    int getCountOfOrders(long lastAuthDate);

    Long getAmountByStatus(Status status, String senderEmail);
}
