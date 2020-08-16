package com.project.controller.controller;

import com.project.model.Order;
import com.project.model.OrderDTO;
import com.project.model.Status;
import com.project.model.UserAccount;
import com.project.service.UserAccountServiceImpl;
import com.project.service.abstraction.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MailController {

    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    UserAccountServiceImpl userAccountService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/confirmEmail")
    public ModelAndView confirmEmail(@RequestParam("token") String tokenToConfirm, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        UserAccount user = userAccountService.findUserByToConfirmEmail(tokenToConfirm);
        if (user != null) {
            if (tokenToConfirm.equals(user.getTokenToConfirmEmail())) {
                user.setEnabled(true);
                user.setTokenToConfirmEmail(null);
                userAccountService.save(user);
            }
        } else {
            modelAndView.addObject("Error", "Bad tokenToConfirmEmail");
        }
        System.out.println(modelAndView.getModelMap());
        if (user.getAutoReg()) {
            authenticateUserAndSetSession(user, request);
            List<Order> orderList = orderService.getOrdersByUserId(user.getId());
            for (Order order : orderList) {
                orderService.confirmOrder(order.getId());
                modelAndView.setViewName("redirect:/shopping-cart");
            }
        } else {
            authenticateUserAndSetSession(user, request);
            modelAndView.setViewName("redirect:/home");
        }


        return modelAndView;
    }

    @GetMapping("/mailToConfirmViev")
    public ModelAndView mailToConfirmViev() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/mail/confirmEmail");
        return modelAndView;
    }

    private void authenticateUserAndSetSession(UserDetails user, HttpServletRequest request) {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        // generate session if one doesn't exist
//        request.getSession().setAttribute("cartId", userAccountService.findByLogin(username).getCart().getId());
        request.getSession().setAttribute("userId", userAccountService.findByLogin(username).getId());
        //request.getSession().setAttribute("cartItems", request.getSession().getAttribute("shoppingcart"));

        token.setDetails(new WebAuthenticationDetails(request));
        //Why dont work such?
        //Authentication authenticatedUser = authenticationManager.authenticate(token);
        Authentication authenticatedUser = token;
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }


}
