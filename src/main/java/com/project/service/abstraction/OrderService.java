package com.project.service.abstraction;

import com.project.model.BookPageAdminDto;
import com.project.model.Order;
import com.project.model.OrderPageAdminDTO;
import com.project.model.Status;
import org.springframework.data.domain.Pageable;

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

    OrderPageAdminDTO getPageOfOrdersByPageable(Pageable pageable, Status status);

    List<Order> findOrderByBookId(Long bookId);
}
