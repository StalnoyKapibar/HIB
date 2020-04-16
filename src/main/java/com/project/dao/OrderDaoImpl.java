package com.project.dao;

import com.project.dao.abstraction.OrderDao;
import com.project.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDaoImpl extends AbstractDao<Long, Order> implements OrderDao {

    OrderDaoImpl(){super(Order.class);}

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> getOrdersByUserId(Long id) {
        return entityManager.createQuery("SELECT b FROM orders b where user_id=:id", Order.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return entityManager.createQuery("SELECT b FROM orders b where b.status=:status", Order.class).setParameter("status", status).getResultList();
    }
}
