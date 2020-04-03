package com.project.config.handler;

import com.project.dao.UserAccountDAO;
import com.project.model.UserAccount;
import com.project.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
@Primary
@AllArgsConstructor
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private UserAccountDAO userAccountDAO;
    private ShoppingCartService shoppingCart;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserAccount user = (UserAccount) authentication.getPrincipal();
        String locale = request.getSession().getAttribute("LANG").toString();
        userAccountDAO.setLocaleAndAuthDate(user.getEmail(), locale, Instant.now().getEpochSecond());
        response.sendRedirect(request.getHeader("referer"));
        shoppingCart.mergeCarts(request, user.getCart().getId());
        request.getSession().setAttribute("userId",user.getUserId());
    }
}
