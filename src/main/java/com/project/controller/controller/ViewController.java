package com.project.controller.controller;


import com.project.model.Book;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.FormLoginErrorMessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
public class ViewController {

    FormLoginErrorMessageService messageService;
    BookService bookService;

    @GetMapping({"/home", "/profile/**", "/category/**", "/shopping-cart", "/search", "/err/**"})
    public String getHomePage() {
        return "user/user-page";
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/search/{categoryId}")
    public ModelAndView getSearchPage(@PathVariable("categoryId") long categoryId, ModelAndView modelAndView) {
        modelAndView.setViewName("user/user-page");
        return modelAndView;
    }

    @GetMapping("/page/{id}")
    public ModelAndView getPage(@PathVariable("id") long id, ModelAndView modelAndView) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            modelAndView.setViewName("redirect:/err/not-found");
            return modelAndView;
        }
            modelAndView.addObject("isPage", 1);
            modelAndView.addObject("book", id);
            modelAndView.addObject("pageBook", book);
            modelAndView.setViewName("user/user-page");
        return modelAndView;
    }

    @GetMapping("/translate")
    public String getTranslatePage() {
        return "translate";
    }

    @GetMapping("/author-list")
    public ModelAndView getAuthors(ModelAndView view) {
        Set<String> authors = bookService.getAuthorSet();
        view.addObject("authors", authors);
        view.setViewName("/user/user");
        return view;
    }

    @GetMapping("/guide-order")
    public ModelAndView getGuideToOrder(ModelAndView view) {
        view.setViewName("/user/user");
        return view;
    }
}

