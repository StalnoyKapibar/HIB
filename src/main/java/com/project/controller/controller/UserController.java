package com.project.controller.controller;

import com.project.model.FormLoginErrorMessageDTO;
import com.project.model.RegistrationUserDTO;
import com.project.service.FormLoginErrorMessageService;
import com.project.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    FormLoginErrorMessageService messageService;

    @PostMapping(value = "/registration", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView createNewUserAccount(@Valid RegistrationUserDTO user, BindingResult result) {
        ModelAndView view = new ModelAndView("registration");
        view.getModelMap().addAttribute("user", user);

        if (result.hasErrors()) {
            view.getModelMap().addAttribute("errorMessageRegistration", messageService.getErrorMessage(result));
            return view;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            view.getModelMap().addAttribute("errorMessageRegistration", messageService.getErrorMessageOnPasswordsDoesNotMatch());
            return view;
        }
        try {
            //Нужна проверка на уникальность логина и пароля!!
                userAccountService.save(user);
        } catch (DataIntegrityViolationException e) {
            if(e.getCause().getCause().getMessage().contains("login")){
                view.getModelMap().addAttribute("errorMessageRegistration", messageService.getErrorMessageOnLoginUIndex());
            }else {
                view.getModelMap().addAttribute("errorMessageRegistration", messageService.getErrorMessageOnEmailUIndex());
            }
            return view;
        }
        view.setViewName("redirect:/home");
        return view;
    }
}
