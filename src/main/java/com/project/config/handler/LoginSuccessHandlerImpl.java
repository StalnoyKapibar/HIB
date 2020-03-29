package com.project.config.handler;

import com.project.model.UserAccount;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        request.getSession().setAttribute("LANG", ((UserAccount) authentication.getPrincipal()).getLocale());

        if (authentication.getAuthorities().contains(userRoleService.getUserRoleByName("ROLE_ADMIN"))) {
            response.sendRedirect("/admin");
        } else {
            //redirect clear url without parameters
            response.sendRedirect(request.getHeader("referer"));
        }
    }
}