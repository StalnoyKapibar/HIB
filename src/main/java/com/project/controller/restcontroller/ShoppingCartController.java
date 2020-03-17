package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class ShoppingCartController {

    private BookService bookService;

    @GetMapping("/cart/size")
    public int getCartSize(HttpSession session) {
        List<BookDTO> cartList = (List<BookDTO>) session.getAttribute("shoppingcart");
        if (cartList != null) {
            return cartList.size();
        } else {
            return 0;
        }
    }

    @GetMapping("/cart")
    public List<BookDTO> getShoppingCart(HttpSession session) {
        return (List<BookDTO>) session.getAttribute("shoppingcart");
    }

    @PostMapping("/cart/{id}")
    public void addToCart(@PathVariable Long id, HttpSession session) {
        List<BookDTO> cartList = (List<BookDTO>) session.getAttribute("shoppingcart");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        cartList.add(bookService.getBookByIdLocale(id));
        session.setAttribute("shoppingcart", cartList);
    }

    @DeleteMapping("/cart/{id}")
    public void deletFromCart(@PathVariable Long id, HttpSession session){
        List<BookDTO> cartList = (List<BookDTO>) session.getAttribute("shoppingcart");
        if (cartList != null) {
            cartList.remove(bookService.getBookByIdLocale(id));
        }
    }

}
