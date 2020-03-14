package com.project.controller.controller;

import com.project.translate.HibTranslatorImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String getHomePage(HttpServletRequest request) throws IOException {
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "ru");
        }
        return "home";
    }

    @GetMapping("/page")
    public String getPage(HttpServletRequest request) {
        if (request.getSession(false) == null) {
            request.getSession(true).setAttribute("LANG", "ru");
        }
        return "page";
    }

    @GetMapping("/translate")
    public String getTranslatePage() {
        return "translate";
    }
}
