package com.project.controller.controller;

import com.project.model.FormLoginErrorMessageDTO;
import com.project.model.RegistrationUserDTO;
import com.project.model.ShoppingCartDTO;
import com.project.service.abstraction.FormLoginErrorMessageService;
import com.project.service.abstraction.ResetPasswordService;
import com.project.service.abstraction.ShoppingCartService;
import com.project.service.abstraction.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Random;

@Controller
public class UserController {
    public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    UserAccountService userAccountService;

    @Autowired
    FormLoginErrorMessageService messageService;
    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/resetPassword")
    public String getResetPasswordPage() {
        return "user/user-page";
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
        ModelAndView view = new ModelAndView("user/user-page");
        view.getModelMap().addAttribute("user", new RegistrationUserDTO());
        view.getModelMap().addAttribute("errorMessage", new FormLoginErrorMessageDTO(false, ""));
        return view;
    }

    @PostMapping(value = "/registration", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView createNewUserAccount(@Valid RegistrationUserDTO user, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("user/user-page");
        view.getModelMap().addAttribute("user", user);
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort());
        if (result.hasErrors()) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessage(result));
            return view;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnPasswordsDoesNotMatch());
            return view;
        }
        if (userAccountService.emailExist(user.getEmail())) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnEmailUIndex());
            return view;
        }
        try {
            userAccountService.save(user, url.toString());
        } catch (DataIntegrityViolationException e) {
            if (e.getCause().getCause().getMessage().contains("login")) {
                view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnLoginUIndex());
            } else {
                view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnEmailUIndex());
            }
            return view;
        }
        //After successfully Creating user

        view.setViewName("redirect:/reqapprove");

        return view;
    }

    @GetMapping("/1clickreg")
    public ModelAndView get1ClickRegistrationPage(RegistrationUserDTO user) {
        ModelAndView view = new ModelAndView("cabinet");
        view.getModelMap().addAttribute("user", new RegistrationUserDTO());
        view.getModelMap().addAttribute("errorMessage", new FormLoginErrorMessageDTO(false, ""));
        return view;
    }

    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    @GetMapping("/reqapprove")
    public ModelAndView requestApproveAuth(RegistrationUserDTO user) {
        return new ModelAndView("requestApproveAuth");
    }
}
