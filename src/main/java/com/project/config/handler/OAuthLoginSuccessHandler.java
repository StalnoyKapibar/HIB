package com.project.config.handler;

import com.project.model.ShoppingCartDTO;
import com.project.model.UserPrincipal;
import com.project.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {
    private ShoppingCartService shoppingCart;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        ShoppingCartDTO cart = (ShoppingCartDTO) httpServletRequest.getSession().getAttribute("shoppingcart");
        if (cart != null) {
            ShoppingCartDTO mainCart = shoppingCart.getCartById(userPrincipal.getCart().getId());
            mainCart.mergeCarts(cart);
            shoppingCart.updateCart(mainCart);
            httpServletRequest.getSession().removeAttribute("shoppingcart");
        }
        String locale = userPrincipal.getLocale();
        httpServletRequest.getSession().setAttribute("LANG", locale);
        httpServletResponse.sendRedirect("/");
    }
}
