package com.project.config.initializer;

import com.project.model.UserAccount;
import com.project.model.UserRole;
import com.project.service.UserAccountService;
import com.project.service.UserRoleService;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

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
        saveUserRole(userRole);
        saveUserRole(adminRole);

        // Admin user. (username = "admin", password = "11")

        UserAccount account1 = new UserAccount();
        account1.setLogin("admin");
        account1.setPassword(encoder.encode("11"));
        account1.setEmail("robolitan12@gmail.com");
        account1.setRoles(SetUtils.hashSet(new UserRole(2l, "ROLE_ADMIN")));
        saveUserAccount(account1);

        // Simple user. (username = "user", password = "11")

        UserAccount account2 = new UserAccount();
        account2.setLogin("user");
        account2.setPassword(encoder.encode("11"));
        account2.setEmail("robo@gmail.com");
        account2.setRoles(SetUtils.hashSet(new UserRole(1l, "ROLE_USER")));
        saveUserAccount(account2);
    }

    private void saveUserAccount(UserAccount userAccount) {
        userAccountService.save(userAccount);
    }

    private void saveUserRole(UserRole userRole) {
        userRoleService.save(userRole);
    }
}
