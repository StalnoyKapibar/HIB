package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.CartItem;
import com.project.model.ShoppingCart;
import com.project.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
public class ShoppingCartController {

    private BookService bookService;

    @GetMapping("/cart/size")
    public int getCartSize(HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingcart");
        if (shoppingCart != null) {
            return shoppingCart.getCartItems().size();
        } else {
            return 0;
        }
    }

    @GetMapping(value = "/cart")
    public List<CartItem> getShoppingCart(HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingcart");
        return shoppingCart.getCartItems();
    }

    @PostMapping("/cart/{id}")
    public void addToCart(@PathVariable Long id, HttpSession session) {
        BookDTO bookDTO = bookService.getBookDTOById(id);
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingcart");
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        List<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem newCartItem = new CartItem(bookDTO, 1);
        if (cartItems.contains(newCartItem)) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.equals(newCartItem)) {
                    newCartItem.setQuantity(cartItem.getQuantity() + 1);
                    cartItems.remove(cartItem);
                    break;
                }
            }
            cartItems.add(newCartItem);
        } else {
            cartItems.add(newCartItem);
        }
        session.setAttribute("shoppingcart", shoppingCart);
    }

    @DeleteMapping("/cart/{id}")
    public void deletFromCart(@PathVariable Long id, HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingcart");
        if (shoppingCart != null) {
            List<CartItem> cartItems = shoppingCart.getCartItems();
            cartItems.remove(new CartItem(bookService.getBookDTOById(id), 0));
            shoppingCart.setCartItems(cartItems);
        }
        session.setAttribute("shoppingcart", shoppingCart);
    }

    @PostMapping(value = "/cart", params = {"id", "quatity"})
    public void editCart(@RequestParam("id") Long id, @RequestParam("quatity") Integer quatity, HttpSession session) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingcart");
        CartItem newCartItem = new CartItem(bookService.getBookDTOById(id), quatity);
        List<CartItem> cartItems = shoppingCart.getCartItems();
        cartItems.remove(newCartItem);
        cartItems.add(newCartItem);
        shoppingCart.setCartItems(cartItems);
        session.setAttribute("shoppingcart", shoppingCart);
    }
}
