package com.project.controller.controller;

import com.project.model.FormLoginErrorMessageDTO;
import com.project.model.RegistrationUserDTO;
import com.project.service.abstraction.FormLoginErrorMessageService;
import com.project.service.abstraction.ResetPasswordService;
import com.project.service.abstraction.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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
    public ModelAndView createNewUserAccount(@Valid RegistrationUserDTO user, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
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
        if (userAccountService.emailExist(user.getEmail())) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnEmailUIndex());
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
        //After successfully Creating user

        authenticateUserAndSetSession(user, request, response);
        view.setViewName("redirect:/home");

        return view;
    }

    private void authenticateUserAndSetSession(RegistrationUserDTO user, HttpServletRequest request, HttpServletResponse response) {
        String username = user.getLogin();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        try {
            request.getSession().setAttribute("cartId1click", userAccountService.findByLogin(username).getCart().getId());
            request.getSession().setAttribute("userId", userAccountService.findByLogin(username).getId());
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authenticatedUser = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        }
        catch (NoResultException | UsernameNotFoundException e) {
            e.printStackTrace();
        }
        //request.getSession().setAttribute("cartItems", request.getSession().getAttribute("shoppingcart"));

    }

    @GetMapping("/1clickreg")
    public ModelAndView get1ClickRegistrationPage(RegistrationUserDTO user) {
        ModelAndView view = new ModelAndView("reg1Click");
        view.getModelMap().addAttribute("user", new RegistrationUserDTO());
        view.getModelMap().addAttribute("errorMessage", new FormLoginErrorMessageDTO(false, ""));
        return view;
    }

    @PostMapping(value = "/1clickreg", consumes =
            {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView createNewUserAccount1Click(@Valid RegistrationUserDTO user, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("reg1Click");
        view.getModelMap().addAttribute("user", user);
        user.setLogin(user.getEmail());
        user.setPassword(generateString(new Random(), SOURCES, 10));
        user.setConfirmPassword(user.getPassword());

        if (userAccountService.findByEmail(user.getEmail())!=null) {
            view.getModelMap().addAttribute("errorMessage",
                    "Такой аккаунт уже существует. Войдите под своими данными.");
            return view;
        }
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
        } catch (MailSendException e) {
            view.setViewName("redirect:/1clickreg");
        }
        authenticateUserAndSetSession(user, request, response);
        view.setViewName("redirect:/shopping-cart");
        return view;
    }

    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }


}
