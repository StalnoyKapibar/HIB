package com.project.config.handler;

import com.project.model.ShoppingCartDTO;
import com.project.model.UserAccount;
import com.project.service.ShoppingCartService;
import com.project.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
@Primary
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private UserRoleService userRoleService;
    private ShoppingCartService shoppingCart;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        ShoppingCartDTO cart = (ShoppingCartDTO) request.getSession().getAttribute("shoppingcart");
        if (cart != null) {
            ShoppingCartDTO mainCart = shoppingCart.getCartById(userAccount.getCart().getId());
            mainCart.mergeCarts(cart);
            shoppingCart.updateCart(mainCart);
            request.getSession().removeAttribute("shoppingcart");
        }
        String locale = userAccount.getLocale();
        request.getSession().setAttribute("LANG", locale);

        if (authentication.getAuthorities().contains(userRoleService.getUserRoleByName("ROLE_ADMIN"))) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/home");
        }
    }
}