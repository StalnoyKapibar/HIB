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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = "REST-API документация для Корзины.")
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class ShoppingCartRestController {

    private BookService bookService;
    private ShoppingCartService cartService;

    @ApiOperation(value = "Получить модель объекта корзина"
            , notes = "Этот ендпойнт возвращает модель объекта корзины - ShoppingCartDTO."
            ,response = ShoppingCartDTO.class
            , tags = "getCartModel")
    @GetMapping("/cart/model")
    public ShoppingCartDTO getCartModel(){
        return new ShoppingCartDTO();
    }

    @ApiOperation(value = "Получить размер корзины"
            , notes = "Этот ендпойнт возвращает количество товаров в корзине."
            ,response = Number.class
            , tags = "getCartSize")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "id корзины", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "shoppingcart", value = "ShoppingCartDTO: объект корзины, добавленный атрибутом в HttpSession", required = true, dataType = "Object", paramType = "query"),
    })
    @GetMapping("/cart/size")
    public int getCartSize(HttpServletRequest request) {
        Long cartId = (Long) request.getSession().getAttribute("cartId");
        if (cartId != null) {
            request.getSession().setAttribute("cartId", cartId);
            return cartService.getCartById(cartId).getCartItems().size();
        } else {
            request.getSession().removeAttribute("cartId");
        }
        ShoppingCartDTO shoppingCart = (ShoppingCartDTO) request.getSession().getAttribute("shoppingcart");
        if (shoppingCart != null) {
            return shoppingCart.getCartItems().size();
        } else {
            request.getSession().setAttribute("shoppingcart", new ShoppingCartDTO());
            return 0;
        }
    }

    @ApiOperation(value = "Получить все товары в корзине"
            , notes = "Эта конечная точка возвращает list с CartItemDTO для этой корзины покупок"
            ,response = CartItemDTO.class
            , responseContainer  =  "List"
            , tags = "getShoppingCart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "id корзины", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "shoppingcart", value = "ShoppingCartDTO: объект корзины, добавленный атрибутом в HttpSession", required = true, dataType = "ShoppingCartDTO", paramType = "query"),
    })
    @PostMapping(value = "/cart")
    public List<CartItemDTO> getShoppingCart(HttpServletRequest request) {
        Long cartId = (Long) request.getSession().getAttribute("cartId");
        ShoppingCartDTO shoppingCart = (ShoppingCartDTO) request.getSession().getAttribute("shoppingcart");
        if (cartId != null) {
            return cartService.getCartById(cartId).getCartItems();
        }
        if (shoppingCart == null) {
            request.getSession().setAttribute("shoppingcart", new ShoppingCartDTO());
            shoppingCart = (ShoppingCartDTO) request.getSession().getAttribute("shoppingcart");
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
    public void addToCart(@PathVariable Long id, HttpServletRequest request) {
        Book book = bookService.getBookById(id);
        if (!book.isShow()) {
            return;
        }
        //TODO вот здесь не видно нашего картайд при 1регистрациипотомую
        Long cartId = (Long) request.getSession().getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDTO shoppingCartDTO = cartService.getCartById(cartId);
            shoppingCartDTO.addCartItem(book);
            cartService.updateCart(shoppingCartDTO);
        }  else {
            ShoppingCartDTO shoppingCart = (ShoppingCartDTO) request.getSession().getAttribute("shoppingcart");
            shoppingCart.addCartItem(book);
            request.getSession().setAttribute("shoppingcart", shoppingCart);
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
    public void deleteFromCart(@PathVariable Long id, HttpServletRequest request) {
        Long cartId = (Long) request.getSession().getAttribute("cartId");
        if (cartId != null) {
            ShoppingCartDTO shoppingCartDTO = cartService.getCartById(cartId);
            Long cartItemDtoId = shoppingCartDTO.deleteCartItem(id);
            cartService.deleteBookFromShopCartCartItem(cartId, cartItemDtoId);
            cartService.deleteCartItem(cartItemDtoId);
        } else {
            ShoppingCartDTO shoppingCart = (ShoppingCartDTO) request.getSession().getAttribute("shoppingcart");
            shoppingCart.deleteCartItem(id);
            request.getSession().setAttribute("shoppingcart", shoppingCart);
        }
    }
}