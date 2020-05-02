package com.project.controller.restcontroller;

import com.project.model.*;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.OrderService;
import com.project.service.abstraction.ShoppingCartService;
import com.project.service.abstraction.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private ShoppingCartService cartService;
    private OrderService orderService;
    private UserAccountService userAccountService;
    private BookService bookService;

    @PostMapping("/order/confirmaddress")
    private OrderDTO addOder(HttpSession httpSession) {
        ShoppingCartDTO shoppingCartDTO = cartService.getCartById((Long) httpSession.getAttribute("cartId"));
        OrderDTO order = new OrderDTO();
        order.setItems(shoppingCartDTO.getCartItems());
        order.setData(LocalDate.now().toString());
        order.setShippingCost(350);
        order.setItemsCost((int) shoppingCartDTO.getTotalCostItems());
        order.setStatus("Processing");
        httpSession.setAttribute("order", order);
        return order;
    }

    @GetMapping("/order")
    private void confirmOrder(HttpSession httpSession) {
        OrderDTO order = (OrderDTO) httpSession.getAttribute("order");
        Long userId = (Long) httpSession.getAttribute("userId");
        order.setUserAccount(userAccountService.getUserById(userId));
        ShoppingCartDTO shoppingCartDTO = cartService.getCartById((Long) httpSession.getAttribute("cartId"));
        shoppingCartDTO.getCartItems().clear();
        cartService.updateCart(shoppingCartDTO);
        orderService.addOrder(order.getOder());
    }

    @GetMapping("/order/getorders")
    private List<OrderDTO> getOrder(HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        List<Order> orderList = orderService.getOrdersByUserId(userId);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderList) {
            orderDTOS.add(order.getOrderDTO());
        }
        return orderDTOS;
    }

    @GetMapping("/order/size")
    private int getOrderSize(HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        return orderService.getOrdersByUserId(userId).size();
    }
}
