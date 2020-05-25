package com.project.service;

import com.project.dao.abstraction.OrderDao;
import com.project.model.*;
import com.project.service.abstraction.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDAO;

    @Override
    public void addOrder(Order order) {
        orderDAO.add(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDAO.update(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDAO.findById(id);
    }

    @Override
    public void deleteOrder(Order order) {
        for (CartItem cartItem : order.getItems()) {
            Book book = cartItem.getBook();
            book.setLastBookOrdered(false);
        }
        orderDAO.update(order);
        orderDAO.delete(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public List<Order> getOrdersByUserId(Long id) {
        return orderDAO.getOrdersByUserId(id);
    }

    @Override
    public List<Order> getOdersByStatus(String status) {
        return orderDAO.getOrdersByStatus(status);
    }

    @Override
    public void completeOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(Status.COMPLETED);
        for (CartItem cartItem : order.getItems()) {
            Book book = cartItem.getBook();
            book.setShow(false);
        }
        orderDAO.update(order);
    }
}
