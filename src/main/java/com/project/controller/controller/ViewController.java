package com.project.controller.controller;

import com.project.translate.HibTranslatorImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/search")
    public ModelAndView getSearchResultPage(@RequestParam("request") String req, HttpServletRequest request, ModelAndView modelAndView){
        modelAndView.addObject("request", req);
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "en");
        }
        modelAndView.setViewName("search");
        return modelAndView;
    }
}
