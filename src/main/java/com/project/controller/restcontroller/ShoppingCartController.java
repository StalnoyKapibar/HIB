package com.project.controller.restcontroller;

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
        Map<Long, Integer> cartList = (Map<Long, Integer>) session.getAttribute("shoppingcart");
        if (cartList != null) {
            return cartList.size();
        } else {
            return 0;
        }
    }

    @GetMapping(value = "/cart")
    public Map<Long, Integer> getShoppingCart(HttpSession session) {
        return (Map<Long, Integer>) session.getAttribute("shoppingcart");
    }

    @PostMapping("/cart/{id}")
    public void addToCart(@PathVariable Long id, HttpSession session) {
        Map<Long, Integer> cartList = (Map<Long, Integer>) session.getAttribute("shoppingcart");
        if (cartList == null) {
            cartList = new HashMap<Long, Integer>();
        }
        cartList.merge(id, 1, (oldVal, newVal) -> oldVal + newVal);
        session.setAttribute("shoppingcart", cartList);
    }

    @DeleteMapping("/cart/{id}")
    public void deletFromCart(@PathVariable Long id, HttpSession session) {
        Map<Long, Integer> cartList = (Map<Long, Integer>) session.getAttribute("shoppingcart");
        if (cartList != null) {
            cartList.remove(id);
        }
        session.setAttribute("shoppingcart", cartList);
    }

    @PostMapping(value = "/cart", params = {"id", "quatity"})
    public void editCart(@RequestParam("id") Long id, @RequestParam("quatity") Integer quatity, HttpSession session) {
        Map<Long, Integer> cartList = (Map<Long, Integer>) session.getAttribute("shoppingcart");
        cartList.replace(id, quatity);
        session.setAttribute("shoppingcart", cartList);
    }
}
