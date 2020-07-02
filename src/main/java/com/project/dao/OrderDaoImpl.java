package com.project.dao;

import com.project.dao.abstraction.OrderDao;
import com.project.model.Order;
import com.project.model.Status;
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
        return entityManager.createQuery("SELECT b FROM orders b where useraccount_id=:id", Order.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return entityManager.createQuery("SELECT b FROM orders b where b.status=:status", Order.class).setParameter("status", status).getResultList();
    }

    @Override
    public List<Order> getOrderByEmailByStatus(Status status, String email) {
        return entityManager
                .createQuery("FROM orders o WHERE status = :status AND user_id = (SELECT id FROM UserAccount ua WHERE email =:email)", Order.class)
                .setParameter("status", status)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public int getCountOfOrders(long lastAuthDate) {
        return entityManager
                .createQuery("SELECT b FROM orders b where b.data>=:data", Order.class)
                .setParameter("data", lastAuthDate)
                .getResultList()
                .size();
    }

    @Override
    public Long getAmountByStatus(Status status, String email) {
        return (Long) entityManager
                .createQuery("SELECT COUNT(*) FROM orders o WHERE status = :status AND user_id = (SELECT id FROM UserAccount ua WHERE email =:email)")
                .setParameter("status", status)
                .setParameter("email", email)
                .getSingleResult();
    }
}
