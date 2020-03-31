package com.project.config.handler;

import com.project.dao.UserAccountDAO;
import com.project.model.ShoppingCartDTO;
import com.project.model.UserPrincipal;
import com.project.service.UserAccountService;
import com.project.service.UserAccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
@AllArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    UserAccountDAO userAccountDAO;

    private ShoppingCartService shoppingCart;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        ShoppingCartDTO cart = (ShoppingCartDTO) httpServletRequest.getSession().getAttribute("shoppingcart");
        if (cart != null) {
            ShoppingCartDTO mainCart = shoppingCart.getCartById(userPrincipal.getCart().getId());
            mainCart.mergeCarts(cart);
            shoppingCart.updateCart(mainCart);
            httpServletRequest.getSession().removeAttribute("shoppingcart");
        }
        String locale = request.getSession().getAttribute("LANG").toString();
        userAccountDAO.setLocaleAndAuthDate(userPrincipal.getEmail(), locale, Instant.now().getEpochSecond());
        response.sendRedirect(request.getHeader("referer"));
    }

}
