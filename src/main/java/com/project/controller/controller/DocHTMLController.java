package com.project.controller.controller;

import com.project.service.abstraction.BookService;
import com.project.service.abstraction.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocHTMLController {

    private final BookService bookService;
    private final OrderService orderService;

    public DocHTMLController(BookService bookService, OrderService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @GetMapping("/mail/book")
    public String getBook(@RequestParam long id, Model model){
        model.addAttribute("book", bookService.getBookById(id) );
        return "mailForm/book";
    }

    @GetMapping("/mail/order")
    public String getOrder(@RequestParam long id, Model model){
        model.addAttribute("orders", orderService.getOrderById(id));
        return "mailForm/order";
    }

    @GetMapping("mail/oneClick")
    public String getOneClick(){
        return "mailForm/oneClickReg";
    }

}
