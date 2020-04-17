package com.project.controller.restcontroller;

import com.project.model.BookDto;
import com.project.model.CartItemDto;
import com.project.model.ShoppingCartDto;
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
    public int getCartSize(HttpSession session, Authentication authentication) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            session.setAttribute("cartId", cartId);
            return cartService.getCartById(cartId).getCartItems().size();
        } else {
            session.removeAttribute("cartId");
        }
        ShoppingCartDto shoppingCart = (ShoppingCartDto) session.getAttribute("shoppingcart");
        if (shoppingCart != null) {
            return shoppingCart.getCartItems().size();
        } else {
            session.setAttribute("shoppingcart", new ShoppingCartDto());
            return 0;
        }
    }

    @GetMapping(value = "/cart")
    public List<CartItemDto> getShoppingCart(HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            return cartService.getCartById(cartId).getCartItems();
        }
        ShoppingCartDto shoppingCart = (ShoppingCartDto) session.getAttribute("shoppingcart");
        return shoppingCart.getCartItems();
    }

    @PostMapping("/cart/{id}")
    public void addToCart(@PathVariable Long id, HttpSession session) {
        BookDto bookDTO = bookService.getBookDTOById(id);
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDto shoppingCartDTO = cartService.getCartById(cartId);
            shoppingCartDTO.addCartItem(bookDTO);
            cartService.updateCart(shoppingCartDTO);
        } else {
            ShoppingCartDto shoppingCart = (ShoppingCartDto) session.getAttribute("shoppingcart");
            shoppingCart.addCartItem(bookDTO);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }

    @DeleteMapping("/cart/{id}")
    public void deletFromCart(@PathVariable Long id, HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDto shoppingCartDTO = cartService.getCartById(cartId);
            cartService.deleteCartItem(shoppingCartDTO.deleteCartItem(id));
        } else {
            ShoppingCartDto shoppingCart = (ShoppingCartDto) session.getAttribute("shoppingcart");
            shoppingCart.deleteCartItem(id);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }

    @PostMapping(value = "/cart", params = {"id", "quatity"})
    public void editCart(@RequestParam("id") Long id, @RequestParam("quatity") Integer quatity, HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDto shoppingCartDTO = cartService.getCartById(cartId);
            shoppingCartDTO.updateCartItem(id, quatity);
            cartService.updateCart(shoppingCartDTO);
        } else {
            ShoppingCartDto shoppingCart = (ShoppingCartDto) session.getAttribute("shoppingcart");
            shoppingCart.updateCartItem(id, quatity);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }
}
