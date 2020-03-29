package com.project.controller.controller;


import com.project.config.handler.LoginFailureHandlerImpl;
import com.project.model.FormLoginErrorMessageDTO;
import com.project.model.RegistrationUserDTO;
import com.project.service.FormLoginErrorMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@AllArgsConstructor
public class ViewController {

    FormLoginErrorMessageService messageService;

    @GetMapping({"/home", "/"})
    public ModelAndView getHomePage(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        view.setViewName("home");
        setErrorMessageOnFailureLogin(request, response, view);
        return view;
    }

    @GetMapping("/page/{id}")
    public ModelAndView getPage(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        view.addObject("book", id);
        view.setViewName("page");
        setErrorMessageOnFailureLogin(request, response, view);
        return view;
    }

    @GetMapping("/registration")
    public ModelAndView getRegistrationPage(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        view.setViewName("registration");
        view.getModelMap().addAttribute("user", new RegistrationUserDTO());
        setErrorMessageOnFailureLogin(request,response,view);
        return view;
    }

    @GetMapping("/translate")
    public String getTranslatePage() {
        return "translate";
    }

    private void setErrorMessageOnFailureLogin(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        if (request.getCookies().length > 1) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals("LOGINFAILURE")) {
                    view = getViewWithErrorMessage(c.getValue(), view);
                }
            }
        }
    }

    private ModelAndView getViewWithErrorMessage(String reason, ModelAndView view) {
        if (reason.equals(LoginFailureHandlerImpl.REASON_NO_VALUE)) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnNoValuePresent());
        }
        if (reason.equals(LoginFailureHandlerImpl.REASON_BAD_CREDENTIAL)) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnBadCredential());
        }
        return view;
    }

}
