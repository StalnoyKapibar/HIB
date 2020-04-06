package com.project.dao;

import com.project.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@AllArgsConstructor
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addOrder(Order order) {
        entityManager.persist(order);
    }

    @Override
    public void updateOrder(Order order) {
        entityManager.merge(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public void deleteOrder(Order order) {
        entityManager.remove(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return entityManager.createQuery("SELECT b FROM orders b", Order.class).getResultList();
    }

    @Override
    public List<Order> getOrdersByUserId(Long id) {
        return entityManager.createQuery("SELECT b FROM orders b where user_id=:id", Order.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<Order> getOdersByStatus(String status) {
        return entityManager.createQuery("SELECT b FROM orders b where b.status=:status", Order.class).setParameter("status", status).getResultList();
    }
}
