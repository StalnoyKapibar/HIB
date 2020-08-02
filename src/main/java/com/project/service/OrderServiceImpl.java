package com.project.service;

import com.project.dao.abstraction.OrderDao;
import com.project.mail.MailService;
import com.project.model.*;
import com.project.service.abstraction.OrderService;
import com.project.service.abstraction.SendEmailService;
import com.project.util.UtilExcelReports;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDAO;
    private SendEmailService sendEmailService;
    private UtilExcelReports excelReports;

    @Override
    public void addOrder(Order order, String  url) {
        orderDAO.add(order);
        try {
            sendEmailService.orderPresent(order, url);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        for (CartItem cartItem : order.getItems()) {
            Book book = cartItem.getBook();
            if (order.getStatus().equals(Status.PROCESSING)) {
                book.setShow(true);
            }
            book.setLastBookOrdered(false);
        }
        order.setStatus(Status.DELETED);
        orderDAO.update(order);
    }

    @Override
    public String cancelOrder(Long id) {
        Order order = getOrderById(id);
        if (order.getStatus().equals(Status.UNPROCESSED)) {
            for (CartItem cartItem : order.getItems()) {
                Book book = cartItem.getBook();
                book.setLastBookOrdered(false);
            }
            order.setStatus(Status.CANCELED);
            orderDAO.update(order);
            return "{\"response\" : \"Order canceled\"}";
        } else {
            return "{\"response\" : \"Order can't be canceled because order is not UNPROCESSED\"}";
        }

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
    public void completeOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(Status.COMPLETED);
        for (CartItem cartItem : order.getItems()) {
            Book book = cartItem.getBook();
            book.setShow(false);
            book.setLastBookOrdered(false);
        }
        orderDAO.update(order);
    }

    @Override
    public List<Order> getOrderByEmailByStatus(Status status, String email) {
        return orderDAO.getOrderByEmailByStatus(status, email);
    }

    @Override
    public void unCompleteOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(Status.PROCESSING);
        for (CartItem cartItem : order.getItems()) {
            Book book = cartItem.getBook();
            book.setShow(true);
            book.setLastBookOrdered(true);
        }
        orderDAO.update(order);
    }

    @Override
    public void processOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus(Status.PROCESSING);
        for (CartItem cartItem : order.getItems()) {
            Book book = cartItem.getBook();
            book.setShow(false);
            book.setLastBookOrdered(true);
        }
        orderDAO.update(order);
    }

    @Override
    public Long[] getAmountOfOrders(String email) {
        Long uprocessedOrders = orderDAO.getAmountByStatus(Status.UNPROCESSED, email);
        Long processingOrders = orderDAO.getAmountByStatus(Status.PROCESSING, email);
        Long completedOrders = orderDAO.getAmountByStatus(Status.COMPLETED, email);
        Long deletedOrders = orderDAO.getAmountByStatus(Status.DELETED, email);
        Long canceledOrders = orderDAO.getAmountByStatus(Status.CANCELED, email);
        return new Long[] {uprocessedOrders, processingOrders, completedOrders, deletedOrders, canceledOrders};
    }

    @Override
    public List<Order> findOrderByBookId(Long bookId) {
        return orderDAO.findOrderByBookId(bookId);
    }

    @Override
    public ResponseEntity createFileAllOrders() {
        excelReports.createFileToServer(orderDAO);
        Path path = Paths.get("export/orders/exportsSales.xlsx");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "export_orders_sales.xlsx" + "\"")
                .body(resource);
    }

    @Override
    public int getCountOfOrders() {
        return orderDAO.getCountOfOrders();
    }

    @Override
    public OrderPageAdminDTO getPageOfOrdersByPageable(Pageable pageable, Status status) {
        return orderDAO.getPageOfOrdersByPageable(pageable, status);
    }
}
