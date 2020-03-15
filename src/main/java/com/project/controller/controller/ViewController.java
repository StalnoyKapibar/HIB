package com.project.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String getHomePage(HttpServletRequest request) {
        HttpSession session;
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "ru");
        }
        return "home";
    }

    @GetMapping("/page/{id}")
    public ModelAndView getPage(@PathVariable("id") long id, HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.addObject("book", id);
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "ru");
        }
        modelAndView.setViewName("page");
        return modelAndView;
    }
}
