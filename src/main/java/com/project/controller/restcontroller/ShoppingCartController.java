package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.CartItemDTO;
import com.project.model.ShoppingCartDTO;
import com.project.model.UserAccount;
import com.project.service.BookService;
import com.project.service.ShoppingCartService;
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
        if (authentication != null) {
            UserAccount userAccount = (UserAccount) authentication.getPrincipal();
            session.setAttribute("cartId", userAccount.getCart().getId());
            return cartService.getCartById(userAccount.getCart().getId()).getCartItems().size();
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

    @GetMapping(value = "/cart")
    public List<CartItemDTO> getShoppingCart(HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            return cartService.getCartById(cartId).getCartItems();
        }
        ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
        return shoppingCart.getCartItems();
    }

    @PostMapping("/cart/{id}")
    public void addToCart(@PathVariable Long id, HttpSession session) {
        BookDTO bookDTO = bookService.getBookDTOById(id);
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDTO shoppingCartDTO = cartService.getCartById(cartId);
            shoppingCartDTO.addCartItem(bookDTO);
            cartService.updateCart(shoppingCartDTO);
        } else {
            ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
            shoppingCart.addCartItem(bookDTO);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }

    @DeleteMapping("/cart/{id}")
    public void deletFromCart(@PathVariable Long id, HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDTO shoppingCartDTO = cartService.getCartById(cartId);
            shoppingCartDTO.deleteCartItem(id);
            cartService.updateCart(shoppingCartDTO);
        } else {
            ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
            shoppingCart.deleteCartItem(id);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }

    @PostMapping(value = "/cart", params = {"id", "quatity"})
    public void editCart(@RequestParam("id") Long id, @RequestParam("quatity") Integer quatity, HttpSession session) {
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDTO shoppingCartDTO = cartService.getCartById(cartId);
            shoppingCartDTO.updateCartItem(id, quatity);
            cartService.updateCart(shoppingCartDTO);
        } else {
            ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
            shoppingCart.updateCartItem(id, quatity);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }
}