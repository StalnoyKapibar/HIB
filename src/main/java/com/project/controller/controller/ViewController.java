package com.project.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String getHomePage(HttpServletRequest request) throws IOException {
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "en");
        }
        return "home";
    }

    @GetMapping("/page/{id}")
    public ModelAndView getPage(@PathVariable("id") long id, HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.addObject("book", id);
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "en");
        }
        modelAndView.setViewName("page");
        return modelAndView;
    }

    @GetMapping("/translate")
    public String getTranslatePage() {
        return "translate";
    }

    @GetMapping("/shoppingcart")
    public String getShoppingCartPage() {
        return "shoppingcartpage";
    }
}
