package com.project.dao;

import com.project.dao.abstraction.OrderDao;
import com.project.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

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
    public List<Order> getOrdersByStatus(Status status) {
        return entityManager.createQuery("SELECT b FROM orders b where b.status=:status", Order.class).setParameter("status", status).getResultList();
    }

    @Override
    public List<Order> getOrderByEmailByStatus(Status status, String email) {
        return entityManager
                .createQuery("FROM orders o WHERE status = :status AND useraccount_id = (SELECT id FROM UserAccount ua WHERE email =:email)", Order.class)
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
                .createQuery("SELECT COUNT(*) FROM orders o WHERE status = :status AND useraccount_id = (SELECT id FROM UserAccount ua WHERE email =:email)")
                .setParameter("status", status)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public OrderPageAdminDTO getPageOfOrdersByPageable(Pageable pageable, Status status) {

        int limitOrderDTOOnPage = pageable.getPageSize();
        int minNumberId = limitOrderDTOOnPage * pageable.getPageNumber();

        List<Order> orderDTOList = entityManager.createQuery("SELECT o FROM orders o WHERE o.status = :status", Order.class)
                .setParameter("status", status)
                .setFirstResult(minNumberId)
                .setMaxResults(limitOrderDTOOnPage)
                .getResultList();
        OrderPageAdminDTO pageableOrderDTO = new OrderPageAdminDTO();
        pageableOrderDTO.setListBookDTO(orderDTOList);
        pageableOrderDTO.setNumberPages(pageable.getPageNumber());
        pageableOrderDTO.setPageableSize(pageable.getPageSize());
        pageableOrderDTO.setTotalPages(getOrdersByStatus(status).size() / limitOrderDTOOnPage);
        return pageableOrderDTO;


    }
}
