package com.project.config.handler;

import com.project.model.UserAccount;
import com.project.model.UserPrincipal;
import com.project.service.UserAccountServiceImpl;
import com.project.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {

    UserAccountServiceImpl userAccountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserAccount user = (UserAccount) authentication.getPrincipal();
        String locale = request.getSession().getAttribute("LANG").toString();
        userAccountService.setLocaleAndAuthDate(user.getEmail(), locale, Instant.now().getEpochSecond());
        response.sendRedirect(request.getHeader("referer"));
    }
}
