package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.*;
import com.project.service.DataEnterInAdminPanelService;
import com.project.service.abstraction.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*")
@Api(tags = "REST-API документ, описывающий взаимодействие с сервисом: заказы")
@RestController
@AllArgsConstructor
public class OrderRestController {

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

    /**
     * click  button 'checkout' (оформить заказ) при оформлении заказа если пользователь авторизован
     */
    @ApiOperation(value = "получить заказ"
            , notes = "Эндпоинт получает параметр request типа HttpServletRequest. " +
            "Достает из запроса данные, обрабатывает их и " +
            "возращает заказ текущего пользователя который лежит в корзине"
            , response = Order.class
            , tags = "confirmAddress")
    @PostMapping("/api/user/order/confirmaddress")
    private OrderDTO confirmAddress(HttpServletRequest request) {
        ShoppingCartDTO shoppingCartDTO = null;
        OrderDTO order = new OrderDTO();
        if (request.getSession().getAttribute("cartId") == null) {
//            shoppingCartDTO = cartService.getCartById((Long) httpSession.getAttribute("cartId1click"));
            order.setItems(((ShoppingCartDTO) request.getSession().getAttribute("shoppingcart")).getCartItems());
            order.setItemsCost((int) ((ShoppingCartDTO) request.getSession().getAttribute("shoppingcart")).getTotalCostItems());
        } else {
            shoppingCartDTO = cartService.getCartById((Long) request.getSession().getAttribute("cartId"));
            order.setItems(shoppingCartDTO.getCartItems());
            order.setItemsCost((int) shoppingCartDTO.getTotalCostItems());
        }
        order.setDate(Instant.now().getEpochSecond());
        order.setShippingCost(350);
        order.setStatus(Status.UNPROCESSED);
        order.setUserAccount(userAccountService.getUserById((Long) request.getSession().getAttribute("userId")));
        request.getSession().setAttribute("order", order);
        return order;
    }

    /**
     * click button 'next' (далее) при оформлении заказа
     */
    @ApiOperation(value = "подтверждение данных пользователя"
            , notes = "Эндпоинт получает параметр contacts типа ContactsOfOrderDTO. " +
            "Эндпоинт возращает обьект типа ContactsOfOrderDTO"
            , response = ContactsOfOrder.class
            , tags = "addContacts")
    @PostMapping("/api/user/order/confirmContacts")
    private ContactsOfOrderDTO addContacts(HttpServletRequest request, @RequestBody ContactsOfOrderDTO contacts) {
        request.getSession().setAttribute("contacts", contacts);
        return contacts;
    }

    @ApiOperation(value = "проверка email на валидность"
            , notes = "Эндпоинт получает параметр contacts типа ContactsOfOrderDTO. " +
            "Эндпоинт возращает обьект типа String"
            , response = String.class
            , tags = "savePersonalInformation")
    @PostMapping("/checkEmail1ClickReg")
    public String savePersonalInformation(@RequestBody ContactsOfOrderDTO contacts) {
        return userAccountService.emailExistForShowError(contacts);
    }

    @ApiOperation(value = "регистрация в один клик"
            , notes = "Эндпоинт получает параметр contacts типа ContactsOfOrderDTO. " +
            "Эндпоинт возращает обьект типа ModelAndView"
            , response = ModelAndView.class
            , tags = "regOneClick")
    @PostMapping("/reg1Click")
    private ModelAndView regOneClick(@Valid RegistrationUserDTO user, @RequestBody ContactsOfOrderDTO contacts, BindingResult result,
                                     HttpServletRequest request) {
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

        ShoppingCartDTO shoppingCart = (ShoppingCartDTO) request.getSession().getAttribute("shoppingcart");
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

            orderDTO.setItemsCost((int) shoppingCart.getTotalCostItems());
            cartService.updateCart(shoppingCart);
            orderDTO.setUserAccount(userAccountService.getUserById(userDTO.getUserId()));
            orderService.addOrder1ClickReg(orderDTO.getOder());
            userAccountService.sendMessageOneClickReg(userAccount, url.toString(), orderDTO, user);
            request.getSession().removeAttribute("shoppingcart");

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


    /**
     * click button 'Buy Now' (купить сейчас) при оформлении заказа
     */
    @ApiOperation(value = "подтверждение заказа"
            , notes = "Эндпоинт получает параметр httpSession типа HttpSession. " +
            "Эндпоинт ничего не возращает"
            , response = Void.class
            , tags = "regOneClick")
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

    @ApiOperation(value = "получить список заказов текщего пользователя"
            , notes = "Эндпоинт получает параметр httpSession типа HttpSession. " +
            "Эндпоинт возращает список заказов"
            , response = OrderDTO.class
            , responseContainer = "List"
            , tags = "getOrder")
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

    @ApiOperation(value = "получить список заказов текщего пользователя"
            , notes = "Эндпоинт получает параметр httpSession типа HttpSession. " +
            "Эндпоинт возращает список заказов"
            , response = OrderDTO.class
            , responseContainer = "List"
            , tags = "getOrder")
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

    @ApiOperation(value = "получить колличество заказов"
            , notes = "Эндпоинт возращает колличество необработанных заказов"
            , response = OrderDTO.class
            , responseContainer = "List"
            , tags = "getOrderSize")
    @GetMapping("/order/size")
    private int getOrderSize(HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        return (int) orderService
                .getOrdersByUserId(userId)
                .stream()
                .filter(order -> order.getStatus() == Status.UNPROCESSED)
                .count();
    }

    @ApiOperation(value = "получить все заказы"
            , notes = "Эндпоинт возращает список OrderDTO"
            , response = OrderDTO.class
            , responseContainer = "List"
            , tags = "getPageOfgetAllOrdersOrdersByStatus")
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

    @ApiOperation(value = "получить страницу заказов по статусу"
            , notes = "Эндпоинт возращает список OrderPageAdminDTO"
            , response = OrderPageAdminDTO.class
            , tags = "getPageOfOrdersByStatus")
    @GetMapping("/api/admin/pageable/{page}/{size}/{status}/{messagesStatus}")
    public OrderPageAdminDTO getPageOfOrdersByStatus(HttpSession session,
                                                     @ApiParam(value = "page", required = true) @PathVariable int page,
                                                     @ApiParam(value = "size", required = true) @PathVariable int size,
                                                     @ApiParam(value = "status", required = true) @PathVariable("status") String statusString,
                                                     @ApiParam(value = "messagesStatus", required = true) @PathVariable("messagesStatus") String messageStatus) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("id")));
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
        if (messageStatus.equals("newMessages")) {
            return orderService.getOrdersNewMessages(page, size, status);
        } else {
            return orderService.getPageOfOrdersByPageable(pageable, status);
        }
    }

    @ApiOperation(value = "получить колличество заказов"
            , notes = "Эндпоинт возращает колличество необработанных заказов"
            , response = int.class
            , tags = "getOrdersCount")
    @GetMapping("/api/admin/order-count")
    private int getOrdersCount(HttpSession session) {
        if (session.getAttribute("data") == null) {
            session.setAttribute("data", dataEnterInAdminPanelService.findById(1L));
        }
        return orderService.getCountOfOrders();
    }

    @ApiOperation(value = "получить заказы по email и по статусу заказа"
            , notes = "Эндпоинт получает параметр email и details типа String, в адресе запроса. " +
            "Эндпоинт возращает список заказов по емейлу и статусу заказа"
            , response = OrderDTO.class
            , responseContainer = "List"
            , tags = "getOrderByEmailByStatus")
    @GetMapping("/api/admin/order/{email}/{details}")
    private List<OrderDTO> getOrderByEmailByStatus(@PathVariable("email") String email, @PathVariable("details") String details) {
        Status status;
        switch (details) {
            case ("unprocessedOrders"):
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

    @ApiOperation(value = "получить колличество заказов"
            , notes = "Эндпоинт получает параметр email типа String, в адресе запроса. " +
            "Эндпоинт возращает массив типа Long в котором будет колличество  всех " +
            "заказов(завершенные, незавершенные, удаленные...)"
            , response = Long.class
            , responseContainer = "Array"
            , tags = "getAmountOfFeedback")
    @GetMapping("/api/admin/order/{email}/amount")
    private Long[] getAmountOfFeedback(@PathVariable("email") String email) {
        return orderService.getAmountOfOrders(email);
    }

    @ApiOperation(value = "изменить статус заказа"
            , notes = "Эндпоинт получает параметр id типа Long, в адресе запроса. " +
            "Эндпоинт ничего не возращает"
            , response = Void.class
            , tags = "orderComplete")
    @PatchMapping("/api/admin/completeOrder/{id}")
    private void orderComplete(@PathVariable Long id) {
        orderService.completeOrder(id);
    }

    @ApiOperation(value = "изменить статус заказа"
            , notes = "Эндпоинт получает параметр id типа Long, в адресе запроса. " +
            "Эндпоинт ничего не возращает"
            , response = Void.class
            , tags = "orderUnComplete")
    @PatchMapping("/api/admin/unCompleteOrder/{id}")
    private void orderUnComplete(@PathVariable Long id) {
        orderService.unCompleteOrder(id);
    }

    @ApiOperation(value = "изменить статус заказа"
            , notes = "Эндпоинт получает параметр id типа Long, в адресе запроса. " +
            "Эндпоинт ничего не возращает"
            , response = Void.class
            , tags = "orderProcess")
    @PatchMapping("/api/admin/processOrder/{id}")
    private void orderProcess(@PathVariable Long id) {
        orderService.processOrder(id);
    }

    @ApiOperation(value = "удалить заказ"
            , notes = "Эндпоинт получает параметр id типа Long, в адресе запроса. " +
            "Эндпоинт ничего не возращает"
            , response = Void.class
            , tags = "orderDelete")
    @PostMapping("/api/admin/deleteOrder/{id}")
    private void orderDelete(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @ApiOperation(value = "получить заказы по идентификатору книги"
            , notes = "Эндпоинт получает параметр id типа Long, в адресе запроса. " +
            "Эндпоинт возращает заказы по идентификатору книги"
            , response = Order.class
            , responseContainer = "List"
            , tags = "getOrders")
    @GetMapping("/api/admin/allorders/{id}")
    public List<Order> getOrders(@PathVariable Long id) {
        return orderService.findOrderByBookId(id);
    }

    @ApiOperation(value = "получить незавершенные заказы по идентификатору книги"
            , notes = "Эндпоинт получает параметр id типа Long, в адресе запроса. " +
            "Эндпоинт возращает незавершенные заказы по идентификатору книги"
            , response = Order.class
            , responseContainer = "List"
            , tags = "getUncompletedOrdersByBookId")
    @GetMapping("/api/admin/orders/uncompleted/{id}")
    public List<Order> getUncompletedOrdersByBookId(@PathVariable Long id) {
        return orderService.findAllUncompletedOrdersByBookId(id);
    }

    @ApiOperation(value = "получить файл всех заказов"
            , notes = "Эндпоинт возращает все заказы"
            , response = ResponseEntity.class
            , tags = "getOrders")
    @GetMapping("/api/admin/sales/")
    public ResponseEntity getOrders() {
        return orderService.createFileAllOrders();
    }

    @ApiOperation(value = "отменить заказ"
            , notes = "Эндпоинт получает параметр id типа Long, в адресе запроса. " +
            "Эндпоинт возращает строку"
            , response = String.class
            , tags = "orderCancel")
    @PostMapping("/api/user/orderCancel/{id}")
    private String orderCancel(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

    @ApiOperation(value = "изменить заказ"
            , notes = "Эндпоинт получает параметр order типа Order. " +
            "Эндпоинт ничего не возращает"
            , response = Void.class
            , tags = "updateOrder"
    )
    @PutMapping("/api/admin/editTrackingNumber/")
    private void updateOrder(@RequestBody Order order) {
        Order order1 = orderService.getOrderById(order.getId());
        order1.setTrackingNumber(order.getTrackingNumber());
        orderService.updateOrder(order1);
    }

    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}
