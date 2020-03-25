package com.project.config.handler;

import com.project.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private UserRoleService userRoleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication.getAuthorities().contains(userRoleService.getUserRoleByName("ROLE_ADMIN"))) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/home");
        }
    }
}