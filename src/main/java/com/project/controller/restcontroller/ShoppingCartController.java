package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ShoppingCartController {

    private BookService bookService;

    @GetMapping("/cart/size")
    public int getCartSize(HttpSession session) {
        Map<BookDTO, Integer> cartList = (Map<BookDTO, Integer>) session.getAttribute("shoppingcart");
        if (cartList != null) {
            return cartList.size();
        } else {
            return 0;
        }
    }

    @GetMapping("/cart")
    public Map<BookDTO, Integer> getShoppingCart(HttpSession session) {
        return (Map<BookDTO, Integer>) session.getAttribute("shoppingcart");
    }

    @PostMapping("/cart/{id}")
    public void addToCart(@PathVariable Long id, HttpSession session) {
        Map<BookDTO, Integer> cartList = (Map<BookDTO, Integer>) session.getAttribute("shoppingcart");
        if (cartList == null) {
            cartList = new HashMap<BookDTO, Integer>();
        }
        cartList.merge(bookService.getBookDTOById(id),1,(oldVal,newVal)->oldVal+newVal);
        session.setAttribute("shoppingcart", cartList);
    }

    @DeleteMapping("/cart/{id}")
    public void deletFromCart(@PathVariable Long id, HttpSession session) {
        Map<BookDTO, Integer> cartList = (Map<BookDTO, Integer>) session.getAttribute("shoppingcart");
        if (cartList != null) {
            cartList.remove(bookService.getBookDTOById(id));
        }
    }

}
