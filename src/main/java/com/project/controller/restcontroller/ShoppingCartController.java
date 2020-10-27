package com.project.controller.restcontroller;

import com.project.model.Book;
import com.project.model.CartItemDTO;
import com.project.model.ShoppingCartDTO;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = "This is the REST-API documentation for Shopping Cart.")
@RestController
@AllArgsConstructor
public class ShoppingCartController {

    private BookService bookService;
    private ShoppingCartService cartService;

    @ApiOperation(value = "Get Cart Size"
            , notes = "Get the quantity  of items in the Shopping Cart"
            ,response = int.class, tags = "getCartSize")
    @GetMapping("/cart/size")
    public int getCartSize(@ApiParam(value = "HttpSession must contain the \"cartId\" attribute with a value of type Long." +
            "And also it must contain the attribute \"shoppingcart\", with a value containing an object of type ShoppingCartDTO."
            , required = true)HttpSession session) {
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
            Long cartItemDtoId = shoppingCartDTO.deleteCartItem(id);
            cartService.deleteBookFromShopCartCartItem(cartId, cartItemDtoId);
            cartService.deleteCartItem(cartItemDtoId);
        } else {
            ShoppingCartDTO shoppingCart = (ShoppingCartDTO) session.getAttribute("shoppingcart");
            shoppingCart.deleteCartItem(id);
            session.setAttribute("shoppingcart", shoppingCart);
        }
    }
}
