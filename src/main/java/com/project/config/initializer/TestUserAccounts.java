package com.project.config.initializer;

import com.project.model.UserAccount;
import com.project.model.UserRole;
import com.project.service.UserAccountService;
import com.project.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUserAccounts {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    PasswordEncoder encoder;


    public void init() {
        // two basic roles : ROLE_ADMIN & ROLE_USER
        UserRole userRole = new UserRole(1l, "ROLE_USER");
        UserRole adminRole = new UserRole(2l, "ROLE_ADMIN");
        userRoleService.save(userRole);
        userRoleService.save(adminRole);

        // Admin user. (username = "admin", password = "admin") ROLE:ADMIN

        UserAccount account1 = new UserAccount();
        account1.setLogin("admin");
        account1.setPassword(encoder.encode("admin"));
        account1.setEmail("admin@gmail.com");
        account1.setFirstName("admin");
        account1.setLastName("admin");
        account1.setRegDate(Instant.now().getEpochSecond());
        account1.setProvider("local");
        account1.setEnabled(true);
        account1.setAuthorities(adminRole);

        userAccountService.save(account1);

        // Simple user. (username = "user", password = "user") ROLE:USER
        UserAccount account2 = new UserAccount();
        account2.setLogin("user");
        account2.setPassword(encoder.encode("user"));
        account2.setEmail("user@gmail.com");
        account2.setFirstName("user");
        account2.setLastName("user");
        account2.setRegDate(Instant.now().getEpochSecond());
        account2.setProvider("local");
        account2.setEnabled(true);
        account2.setAuthorities(userRole);
        userAccountService.save(account2);
    }

}
