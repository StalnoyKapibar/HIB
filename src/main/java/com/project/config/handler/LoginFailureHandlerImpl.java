package com.project.config.handler;

import com.project.service.FormLoginErrorMessageService;
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
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception.getMessage().contains("No value")) {
            response.sendRedirect("/error?failure=NoValuePresentException");
        }
        if (exception.getMessage().contains("Bad credential")) {
            response.sendRedirect("/error?failure=BadCredentialsException");
        }
    }
}
