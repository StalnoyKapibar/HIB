package com.project.dao;

import com.project.dao.abstraction.OrderDao;
import com.project.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl extends AbstractDao<Long, Order> implements OrderDao {

    OrderDaoImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> getOrdersByUserId(Long id) {
        System.out.println("*************************"+id);
        return entityManager.createQuery("SELECT b FROM orders b where user_id=:id", Order.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return entityManager.createQuery("SELECT b FROM orders b where b.status=:status", Order.class).setParameter("status", status).getResultList();
    }

    @Override
    public int getCountOfOrders(long lastAuthDate) {
        return entityManager
                .createQuery("SELECT b FROM orders b where b.data>=:data", Order.class)
                .setParameter("data", lastAuthDate)
                .getResultList()
                .size();
    }
}
