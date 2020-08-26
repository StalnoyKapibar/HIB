package com.project.controller.restcontroller;

import com.project.model.*;
import com.project.service.DataEnterInAdminPanelService;
import com.project.service.abstraction.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@AllArgsConstructor
public class OrderController {

    private ShoppingCartService cartService;
    private OrderService orderService;
    private UserAccountService userAccountService;
    private BookService bookService;
    private DataEnterInAdminPanelService dataEnterInAdminPanelService;
    private UserService userService;
    public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    @Autowired
    FormLoginErrorMessageService messageService;


    @PostMapping("/api/user/order/confirmaddress")
    private OrderDTO addOder(HttpSession httpSession) {
        ShoppingCartDTO shoppingCartDTO = null;
        OrderDTO order = new OrderDTO();
        if (httpSession.getAttribute("cartId") == null) {
//            shoppingCartDTO = cartService.getCartById((Long) httpSession.getAttribute("cartId1click"));
            order.setItems(((ShoppingCartDTO) httpSession.getAttribute("shoppingcart")).getCartItems());
            order.setItemsCost((int) ((ShoppingCartDTO) httpSession.getAttribute("shoppingcart")).getTotalCostItems());
        } else {
            shoppingCartDTO = cartService.getCartById((Long) httpSession.getAttribute("cartId"));
            order.setItems(shoppingCartDTO.getCartItems());
            order.setItemsCost((int) shoppingCartDTO.getTotalCostItems());
        }
        order.setDate(Instant.now().getEpochSecond());
        order.setShippingCost(350);
        order.setStatus(Status.UNPROCESSED);
        Long userId = (Long) httpSession.getAttribute("userId");
        order.setUserAccount(userAccountService.getUserById(userId));
        httpSession.setAttribute("order", order);
        return order;
    }

    @PostMapping("/api/user/order/confirmContacts")
    private ContactsOfOrderDTO addContacts(HttpSession httpSession, @RequestBody ContactsOfOrderDTO contacts) {
        httpSession.setAttribute("contacts", contacts);
        return contacts;
    }

    @PostMapping("/checkEmail1ClickReg")
    public String savePersonalInformation(@RequestBody ContactsOfOrderDTO contacts) {
        return userAccountService.emailExistForShowError(contacts);
    }

    @PostMapping("/reg1Click")
    private ModelAndView regOneClick(@Valid RegistrationUserDTO user, @RequestBody ContactsOfOrderDTO contacts, BindingResult result,
                                     HttpServletRequest request, HttpSession session) {
        ModelAndView view = new ModelAndView("user/user-page");
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort());
        view.getModelMap().addAttribute("user", user);
        user = userService.converterContactsToRegistrationUser(contacts);
        user.setPassword(generateString(new Random(), SOURCES, 10));
        user.setConfirmPassword(user.getPassword());
        user.setAutoReg(true);

        ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
        if (result.hasErrors()) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessage(result));
            return view;
        }
        if (userAccountService.emailExist(user.getEmail())) {
            view.getModelMap().addAttribute("errorMessage",
                    messageService.getErrorMessageOnEmailUIndex());
            return view;
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnPasswordsDoesNotMatch());
            return view;
        }
        try {
            UserAccount userAccount = userAccountService.save1Clickreg(user);
            shoppingCart.setId(userAccountService.getCartIdByUserEmail(user.getEmail()));

            OrderDTO orderDTO = orderService.addOrderReg1Click(shoppingCart, user, contacts);
            UserDTO userDTO = userService.getUserDTOByEmail(user.getEmail(), false);

            cartService.updateCart(shoppingCart);
            orderDTO.setUserAccount(userAccountService.getUserById(userDTO.getUserId()));
            orderService.addOrder1ClickReg(orderDTO.getOder());
            userAccountService.sendMessageOneClickReg(userAccount, url.toString(), orderDTO, user);
            session.removeAttribute("shoppingcart");

        } catch (DataIntegrityViolationException e) {
            if (e.getCause().getCause().getMessage().contains("login")) {
                view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnLoginUIndex());
            } else {
                view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnEmailUIndex());
            }
            return view;
        } catch (MailSendException e) {
            view.setViewName("redirect:/err/not_found");
        }
        return view;
    }


    @PostMapping("/order")
    private void confirmOrder(HttpSession httpSession, HttpServletRequest request) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        OrderDTO order = (OrderDTO) httpSession.getAttribute("order");
        ContactsOfOrderDTO contacts = (ContactsOfOrderDTO) httpSession.getAttribute("contacts");
        order.setContacts(contacts);
        order.setComment(contacts.getComment());
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort());
        if (httpSession.getAttribute("cartId") == null) {
            order.setItems(((ShoppingCartDTO) httpSession.getAttribute("shoppingcart")).getCartItems());
            for (int i = 1; i <= ((ShoppingCartDTO) httpSession.getAttribute("shoppingcart")).getCartItems().size(); i++) {
                order.getItems().get(i - 1).setId((long) i + cartService.getMaxIdCartItem().size());
                shoppingCartDTO.addCartItem(order.getItems().get(i - 1).getBook());
            }
            cartService.updateCart(shoppingCartDTO);
        } else {
            shoppingCartDTO = cartService.getCartById((Long) httpSession.getAttribute("cartId"));
            shoppingCartDTO.getCartItems().clear();
            cartService.updateCart(shoppingCartDTO);
        }
        orderService.addOrder(order.getOder(), url.toString());
        httpSession.removeAttribute("shoppingcart");
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
    @GetMapping("/order/pageable/{page}/{size}")
    private List<OrderDTO> getOrderUser(HttpSession session, @PathVariable int page, @PathVariable int size) {
        Long userId = (Long) session.getAttribute("userId");
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.by("id")));
        List<Order> orderList = orderService.getPageOfOrdersUserByPageable(pageable, userId);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderList) {
            orderDTOS.add(order.getOrderDTO());
        }
        return orderDTOS;
    }
    @GetMapping("/order/size")
    private int getOrderSize(HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        return (int) orderService
                .getOrdersByUserId(userId)
                .stream()
                .filter(order -> order.getStatus() == Status.UNPROCESSED)
                .count();
    }

    @GetMapping("/api/admin/getAllOrders")
    private List<OrderDTO> getAllOrders(HttpSession session) {
        List<Order> orderList = orderService.getAllOrders();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderList) {
            orderDTOS.add(order.getOrderDTOForAdmin());
        }

        DataEnterInAdminPanel data = (DataEnterInAdminPanel) session.getAttribute("data");
        data.setDataEnterInOrders(Instant.now().getEpochSecond());
        dataEnterInAdminPanelService.update(data);
        session.setAttribute("data", data);
        return orderDTOS;
    }

    @GetMapping("/api/admin/pageable/{page}/{size}/{status}")
    public OrderPageAdminDTO getPageOfOrdersByStatus(HttpSession session, @PathVariable int page, @PathVariable int size, @PathVariable("status") String statusString) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("id")));
        Status status;
        try {
            status = Status.valueOf(statusString);
        } catch (Exception e) {
            status = null;
        }
        DataEnterInAdminPanel data = (DataEnterInAdminPanel) session.getAttribute("data");
        data.setDataEnterInOrders(Instant.now().getEpochSecond());
        dataEnterInAdminPanelService.update(data);
        session.setAttribute("data", data);
        return orderService.getPageOfOrdersByPageable(pageable, status);
    }

    @GetMapping("/api/admin/order-count")
    private int getOrdersCount(HttpSession session) {
        if (session.getAttribute("data") == null) {
            session.setAttribute("data", dataEnterInAdminPanelService.findById(1L));
        }
        return orderService.getCountOfOrders();
    }

    @GetMapping("/api/admin/order/{email}/{details}")
    private List<OrderDTO> getOrderByEmailByStatus(@PathVariable("email") String email, @PathVariable("details") String details) {
        Status status;
        switch (details) {
            case ("uprocessedOrders"):
                status = Status.UNPROCESSED;
                break;
            case ("processingOrders"):
                status = Status.PROCESSING;
                break;
            case ("completedOrders"):
                status = Status.COMPLETED;
                break;
            case ("deletedOrders"):
                status = Status.DELETED;
                break;
            case ("canceledOrders"):
                status = Status.CANCELED;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + details);
        }
        List<Order> orders = orderService.getOrderByEmailByStatus(status, email);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            orderDTOS.add(order.getOrderDTOForAdmin());
        }
        return orderDTOS;
    }

    @GetMapping("/api/admin/order/{email}/amount")
    private Long[] getAmountOfFeedback(@PathVariable("email") String email) {
        return orderService.getAmountOfOrders(email);
    }

    @PatchMapping("/api/admin/completeOrder/{id}")
    private void orderComplete(@PathVariable Long id) {
        orderService.completeOrder(id);
    }

    @PatchMapping("/api/admin/unCompleteOrder/{id}")
    private void orderUnComplete(@PathVariable Long id) {
        orderService.unCompleteOrder(id);
    }

    @PatchMapping("/api/admin/processOrder/{id}")
    private void orderProcess(@PathVariable Long id) {
        orderService.processOrder(id);
    }

    @PostMapping("/api/admin/deleteOrder/{id}")
    private void orderDelete(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/api/admin/allorders/{id}")
    public List<Order> getOrders(@PathVariable Long id) {
        return orderService.findOrderByBookId(id);
    }

    @GetMapping("/api/admin/orders/uncompleted/{id}")
    public List<Order> getUncompletedOrdersByBookId(@PathVariable Long id) {
        return orderService.findAllUncompletedOrdersByBookId(id);
    }

    @GetMapping("/api/admin/sales/")
    public ResponseEntity getOrders() {
        return orderService.createFileAllOrders();
    }

    @PostMapping("/api/user/orderCancel/{id}")
    private String orderCancel(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}
