package com.project.config.handler;

import com.project.service.abstraction.FormLoginErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandlerImpl implements AuthenticationFailureHandler {

    @Autowired
    FormLoginErrorMessageService messageService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception.getMessage().contains("No value") || exception.getMessage().contains("No entity")) {
            response.sendRedirect("/error?failure=NoValuePresentException");
        }
        if (exception.getMessage().contains("Bad credential")) {
            response.sendRedirect("/error?failure=BadCredentialsException");
        }
        if (exception.getMessage().contains("User is disabled")) {
            response.sendRedirect("/error?failure=UserIsDisabled");
        }

    }
}
