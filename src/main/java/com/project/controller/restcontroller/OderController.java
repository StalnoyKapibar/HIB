package com.project.controller.restcontroller;

import com.project.model.AddressDto;
import com.project.model.Order;
import com.project.model.OrderDto;
import com.project.model.ShoppingCartDto;
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
public class OderController {

    private ShoppingCartService cartService;
    private OrderService orderService;
    private UserAccountService userAccountService;


    @PostMapping("/order/confirmaddress")
    private OrderDto addOder(@RequestBody AddressDto addressDTO, HttpSession httpSession) {
        ShoppingCartDto shoppingCartDTO = cartService.getCartById((Long) httpSession.getAttribute("cartId"));
        OrderDto order = new OrderDto();
        order.setItems(shoppingCartDTO.getCartItems());
        order.setAddress(addressDTO);
        order.setData(LocalDate.now().toString());
        order.setShippingCost(350);
        order.setItemsCost((int) shoppingCartDTO.getTotalCostItems());
        order.setStatus("Processing");
        httpSession.setAttribute("order", order);
        return order;
    }

    @GetMapping("/order")
    private void confirmOrder(HttpSession httpSession) {
        OrderDto order = (OrderDto) httpSession.getAttribute("order");
        Long userId = (Long) httpSession.getAttribute("userId");
        order.setUserAccount(userAccountService.getUserById(userId));
        ShoppingCartDto shoppingCartDTO = cartService.getCartById((Long) httpSession.getAttribute("cartId"));
        shoppingCartDTO.getCartItems().clear();
        cartService.updateCart(shoppingCartDTO);
        orderService.addOrder(order.getOder());
    }

    @GetMapping("/order/getorders")
    private List<OrderDto> getOrder(HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        List<Order> orderList = orderService.getOrdersByUserId(userId);
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orderList) {
            orderDtos.add(order.getOrderDTO());
        }
        return orderDtos;
    }
}
