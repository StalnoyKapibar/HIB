package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.Book;
import com.project.model.CartItemDTO;
import com.project.model.ShoppingCartDTO;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.ShoppingCartService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = "REST-API документация для Корзины.")
@RestController
@AllArgsConstructor
public class ShoppingCartRestController {

    private BookService bookService;
    private ShoppingCartService cartService;

    @ApiOperation(value = "Получить размер корзины"
            , notes = "Этот ендпойнт возвращает количество товаров в корзине."
            ,response = int.class
            , tags = "getCartSize")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "id корзины", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "shoppingcart", value = "ShoppingCartDTO: объект корзины, добавленный атрибутом в HttpSession", required = true, dataType = "Object", paramType = "query"),
    })
    @GetMapping("/cart/size")
    public int getCartSize(HttpSession session) {
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

    @ApiOperation(value = "Получить все товары в корзине"
            , notes = "Эта конечная точка возвращает list с CartItemDTO для этой корзины покупок"
            ,response = CartItemDTO.class
            , responseContainer  =  "List"
            , tags = "getListCartItemDTO")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "id корзины", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "shoppingcart", value = "ShoppingCartDTO: объект корзины, добавленный атрибутом в HttpSession", required = true, dataType = "ShoppingCartDTO", paramType = "query"),
    })
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

    @ApiOperation(value = "Добавить книгу в корзину"
            , notes = "Этот ендпойнт для добавления книги в корзину"
            ,response = Void.class
            , tags = "addToCart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "id корзины", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "shoppingcart", value = "ShoppingCartDTO: объект корзины, добавленный атрибутом в HttpSession", required = true, dataType = "ShoppingCartDTO", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id книги", required = true, dataType = "Long", paramType = "query")
    })
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

    @ApiOperation(value = "Удалить книгу из корзины"
            , notes = "Этот endpoint удаляет книгу из корзины"
            ,response = Void.class
            , tags = "deleteFromCart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "id корзины", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "shoppingcart", value = "ShoppingCartDTO: объект корзины, доавленный атрибутом в HttpSession", required = true, dataType = "ShoppingCartDTO", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id книги", required = true, dataType = "Long", paramType = "query")
    })
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