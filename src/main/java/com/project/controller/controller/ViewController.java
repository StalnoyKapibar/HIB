package com.project.controller.controller;

import com.project.model.FormLoginErrorMessageDTO;
import com.project.model.RegistrationUserDTO;
import com.project.service.FormLoginErrorMessageService;
import com.project.service.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ViewController {

    FormLoginErrorMessageService messageService;

    UserAccountService userAccountService;

    @GetMapping("/home")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/page/{id}")
    public ModelAndView getPage(@PathVariable("id") long id, ModelAndView modelAndView) {
        modelAndView.addObject("book", id);
        modelAndView.setViewName("page");
        return modelAndView;
    }

    @GetMapping("/translate")
    public String getTranslatePage() {
        return "translate";
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
        if (!user.getPassword().equals(user.getConformPassword())) {
            view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnPasswordsDoesNotMatch());
            return view;
        }
        try {
            userAccountService.save(user);
        } catch (DataIntegrityViolationException e) {
            if(e.getCause().getCause().getMessage().contains("login")){
                view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnLoginUIndex());
            }else {
                view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnEmailUIndex());
            }
            return view;
        }
        view.setViewName("redirect:/home");
        return view;
    }

}
