package com.project.config.handler;

import com.project.service.FormLoginErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandlerImpl implements AuthenticationFailureHandler {

    public static final String REASON_BAD_CREDENTIAL = "BAD_CREDENTIAL";
    public static final String REASON_NO_VALUE = "NO_VALUE_PRESENT";
    private static final Cookie LOGIN_FAILURE_COOKIE = new Cookie("LOGINFAILURE", null);

    @Autowired
    FormLoginErrorMessageService messageService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
       //Time to send cookie (just to controller)
        LOGIN_FAILURE_COOKIE.setMaxAge(1);
        if (exception.getMessage().contains("No value")) {
            LOGIN_FAILURE_COOKIE.setValue(REASON_NO_VALUE);
        }
        if (exception.getMessage().contains("Bad credential")) {
            LOGIN_FAILURE_COOKIE.setValue(REASON_BAD_CREDENTIAL);
        }
        response.addCookie(LOGIN_FAILURE_COOKIE);
        response.sendRedirect(request.getHeader("referer"));
    }
}
