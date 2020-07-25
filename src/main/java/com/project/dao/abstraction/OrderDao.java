package com.project.dao.abstraction;

import com.project.model.Order;
import com.project.model.OrderPageAdminDTO;
import com.project.model.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDao extends GenericDao<Long, Order> {

    List<Order> getOrdersByUserId(Long id);

    List<Order> getOrdersByStatus(Status status);

    List<Order> getOrderByEmailByStatus(Status status, String email);

    int getCountOfOrders();

    Long getAmountByStatus(Status status, String email);

    OrderPageAdminDTO getPageOfOrdersByPageable(Pageable pageable, Status status);

    List<Order> findOrderByBookId(Long bookId);

}
