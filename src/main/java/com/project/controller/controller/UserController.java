package com.project.controller.controller;

import com.project.model.FormLoginErrorMessageDTO;
import com.project.model.RegistrationUserDTO;
import com.project.service.abstraction.FormLoginErrorMessageService;
import com.project.service.abstraction.ResetPasswordService;
import com.project.service.abstraction.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    FormLoginErrorMessageService messageService;

    @GetMapping("/resetPassword")
    public String getResetPasswordPage() {
        return "resetPassword";
    }

    @GetMapping("/resPass")
    public String getPageResPass(@RequestParam(required = false, name = "token") String token) {
        if (resetPasswordService.checkTokenResetPassword(token)) {
            return "pageResPass";
        } else {
            throw new AccessDeniedException("403");
        }
    }

    @GetMapping("/registration")
    public ModelAndView getRegistrationPage(RegistrationUserDTO user) {
        ModelAndView view = new ModelAndView("registration");
        view.getModelMap().addAttribute("user", new RegistrationUserDTO());
        view.getModelMap().addAttribute("errorMessage", new FormLoginErrorMessageDTO(false, ""));
        return view;
    }

    @PostMapping(value = "/registration", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView createNewUserAccount(@Valid RegistrationUserDTO user, BindingResult result) {
        ModelAndView view = new ModelAndView("registration");
        view.getModelMap().addAttribute("user", user);

        if (result.hasErrors()) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessage(result));
            return view;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnPasswordsDoesNotMatch());
            return view;
        }
        try {
            userAccountService.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause().getCause().getMessage().contains("login")) {
                view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnLoginUIndex());
            } else {
                view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnEmailUIndex());
            }
            return view;
        }
        view.setViewName("redirect:/home");
        return view;
    }


}
