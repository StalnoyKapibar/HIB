package com.project.dao.abstraction;

import com.project.model.Order;

import java.util.List;

public interface OrderDao extends IGenericDao<Long, Order>{

    List<Order> getOrdersByUserId(Long id);

    List<Order> getOrdersByStatus(String status);
}
