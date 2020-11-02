package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.RegistrationDTO;
import com.project.model.UserAccount;
import com.project.model.UserDTOFeedbacksOrders;
import com.project.service.abstraction.FeedbackRequestService;
import com.project.service.abstraction.OrderService;
import com.project.service.abstraction.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "REST-API документ, " +
        "описывающий сервис взаимодействия с аккаунтами пользователей   со страницы админа" +
        "(получение статуса заказов и feedback-ов пользователей, " +
        "получение аккаунта пользователя по логину, " +
        "включение, выключение аккаунта пользователя)")
@RestController
@AllArgsConstructor
public class UserAccountRestController {
    private final UserAccountService userAccountService;
    private final FeedbackRequestService feedbackRequestService;
    private final OrderService orderService;

    @ApiOperation(value = "Получить list объектов UserDTOFeedbacksOrders"
            , notes = "Ендпойнт получает параметр status типа boolean," +
            " соответствующий полю isEnabled в объекте UserAccount." +
            " Ендпойнт вернёт list объектов UserDTOFeedbacksOrders."
            , response = UserDTOFeedbacksOrders.class
            , responseContainer = "List"
            , tags = "getUsersEmailsByStatus")
    @GetMapping("/api/admin/userAccount")
    public List<UserDTOFeedbacksOrders> getUsersEmailsByStatus(@RequestParam Boolean status) {
        List<UserDTOFeedbacksOrders> userDTOFeedbacksOrdersArrayList = new ArrayList<>();
        List<String> users = userAccountService.getUsersEmailsByStatus(status);
        for (String email : users) {
            Long[] feedbacks = feedbackRequestService.getAmountOfFeedback(email);
            Long[] orders = orderService.getAmountOfOrders(email);
            userDTOFeedbacksOrdersArrayList.add(new UserDTOFeedbacksOrders(email, feedbacks[0], feedbacks[1], orders[0], orders[1], orders[2], orders[3]));
        }
        return userDTOFeedbacksOrdersArrayList;
    }

    @ApiOperation(value = "Получить аккаунт пользователя по email"
            , notes = "Ендпойнт получает параметр email типа string, в адресе запроса" +
            " Ендпойнт вернёт аккаунт пользователя UserAccount."
            , response = UserAccount.class
            , tags = "getUserAccount")
    @GetMapping("/api/admin/user/{email}")
    public UserAccount getUserAccount(@PathVariable("email") String email) {
        return userAccountService.findByLogin(email);
    }

    @ApiOperation(value = "Включить, выключить аккаунт пользователя."
            , notes = "Ендпойнт получает параметр email типа string и параметр status типа boolean в адресе запроса" +
            " Ендпойнт вернёт обновлённый аккаунт пользователя UserAccount."
            , response = UserAccount.class
            , tags = "editUserAccount")
    @PatchMapping("/api/admin/user/{email}/{status}")
    public UserAccount editUserAccount(@PathVariable("email") String email, @PathVariable("status") String status) {
        UserAccount userAccount = userAccountService.findByLogin(email);
        if (status.equalsIgnoreCase("disable")) {
            userAccount.setEnabled(false);
        }
        if (status.equalsIgnoreCase("enable")) {
            userAccount.setEnabled(true);
        }
        return userAccountService.update(userAccount);
    }
}
