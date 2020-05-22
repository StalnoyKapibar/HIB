package com.project.controller.restcontroller;

import com.project.model.Book;
import com.project.model.CartItemDTO;
import com.project.model.ShoppingCartDTO;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
public class ShoppingCartController {

    private BookService bookService;
    private ShoppingCartService cartService;

    @GetMapping("/cart/size")
    //TODO вот здесь надо правильно вернуть размер корзины
    public int getCartSize(HttpSession session, Authentication authentication) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            session.setAttribute("cartId", cartId);
            return cartService.getCartById(cartId).getCartItems().size();
        } else {
            session.removeAttribute("cartId");
        }
        ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
        if (shoppingCart != null) {
            return shoppingCart.getCartItems().size();
        } else {
            session.setAttribute("shoppingcart", new ShoppingCartDTO());
            return 0;
        }
    }

    @PostMapping(value = "/cart")
    public List<CartItemDTO> getShoppingCart(HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
        if (cartId != null) {
            return cartService.getCartById(cartId).getCartItems();
        }
        if (shoppingCart == null) {
            session.setAttribute("shoppingcart", new ShoppingCartDTO());
            shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
        }
        return shoppingCart.getCartItems();
    }

    @PostMapping("/cart/{id}")
    public void addToCart(@PathVariable Long id, HttpSession session) {
        Book book = bookService.getBookById(id);
        if (!book.isShow()) {
            return;
        }
        //TODO вот здесь не видно нашего картайд при 1регистрациипотомую
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDTO shoppingCartDTO = cartService.getCartById(cartId);
            shoppingCartDTO.addCartItem(book);
            cartService.updateCart(shoppingCartDTO);
        }  else {
            ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
            shoppingCart.addCartItem(book);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }

    @DeleteMapping("/cart/{id}")
    public void deleteFromCart(@PathVariable Long id, HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDTO shoppingCartDTO = cartService.getCartById(cartId);
            cartService.deleteCartItem(shoppingCartDTO.deleteCartItem(id));
        } else {
            ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
            shoppingCart.deleteCartItem(id);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }
}
