package com.project.controller.controller;

import com.project.model.UserAccount;
import com.project.service.UserAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MailController {
    @Autowired
    UserAccountServiceImpl userAccountService;

    @GetMapping("/confirmEmail")
    public ModelAndView confirmEmail(@RequestParam("token") String tokenToConfirm) {
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
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/mailToConfirmViev")
    public ModelAndView mailToConfirmViev() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/mail/confirmEmail");
        return modelAndView;
    }
}
