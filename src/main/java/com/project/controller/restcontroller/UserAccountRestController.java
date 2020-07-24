package com.project.controller.restcontroller;

import com.project.model.UserAccount;
import com.project.model.UserDTOFeedbacksOrders;
import com.project.service.abstraction.FeedbackRequestService;
import com.project.service.abstraction.OrderService;
import com.project.service.abstraction.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserAccountRestController {
    private final UserAccountService userAccountService;
    private final FeedbackRequestService feedbackRequestService;
    private final OrderService orderService;

    @GetMapping("/api/admin/userAccount")
    public List<UserDTOFeedbacksOrders> getUsersEmails() {
        List<UserDTOFeedbacksOrders> userDTOFeedbacksOrdersArrayList = new ArrayList<>();
        List<String> users = userAccountService.getUsersEmails();
        for (String email : users) {
            Long[] feedbacks = feedbackRequestService.getAmountOfFeedback(email);
            Long[] orders = orderService.getAmountOfOrders(email);
            userDTOFeedbacksOrdersArrayList.add(new UserDTOFeedbacksOrders(email, feedbacks[0], feedbacks[1], orders[0], orders[1], orders[2], orders[3]));
        }
        return userDTOFeedbacksOrdersArrayList;
    }

    @GetMapping("/api/admin/user/{email}")
    public UserAccount getUserAccount(@PathVariable("email") String email) {
        return userAccountService.findByLogin(email);
    }

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

    @DeleteMapping("/api/admin/user/{email}")
    public boolean deleteUserAccount(@PathVariable("email") String email) {
        return userAccountService.deleteUser(email);
    }
}
