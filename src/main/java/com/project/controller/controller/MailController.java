package com.project.controller.controller;

import com.project.model.UserAccount;
import com.project.service.UserAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MailController {

    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    UserAccountServiceImpl userAccountService;

    @GetMapping("/confirmEmail")
    public ModelAndView confirmEmail(@RequestParam("token") String tokenToConfirm, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        UserAccount user = userAccountService.findUserByToConfirmEmail(tokenToConfirm);
        if (user != null) {
            if (tokenToConfirm.equals(user.getTokenToConfirmEmail())) {
                user.setEnabled(true);
                userAccountService.save(user);
            }
        } else {
            modelAndView.addObject("Error", "Bad tokenToConfirmEmail");
        }
        System.out.println(modelAndView.getModelMap());
        if (user.getAutoReg()) {
            modelAndView.setViewName("redirect:/shopping-cart");
        } else {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/mailToConfirmViev")
    public ModelAndView mailToConfirmViev() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/mail/confirmEmail");
        return modelAndView;
    }


}
