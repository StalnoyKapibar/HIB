package com.project.config.handler;

import com.project.dao.UserAccountDao;
import com.project.model.UserAccount;
import com.project.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
@Primary
@AllArgsConstructor
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private UserAccountDao userAccountDAO;
    private ShoppingCartService shoppingCart;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserAccount user = (UserAccount) authentication.getPrincipal();
        String locale = request.getSession().getAttribute("LANG").toString();
        userAccountDAO.setLocaleAndAuthDate(user.getEmail(), locale, Instant.now().getEpochSecond());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("CURRENT_PAGE")) {
                    String currentPage = cookie.getValue();
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    response.sendRedirect(currentPage);
                }
            }
        }
        shoppingCart.mergeCarts(request, user.getCart().getId());
        request.getSession().setAttribute("userId", user.getUserId());
    }

}
