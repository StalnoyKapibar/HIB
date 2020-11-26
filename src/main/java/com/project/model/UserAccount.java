package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Builder
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserAccount implements UserDetails, OAuth2User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @EqualsAndHashCode.Include
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private long regDate;
    private long lastAuthDate;
    private String provider;
    private String locale;
    private String phone;
    private boolean isEnabled = false;
    private boolean autoReg = false;
    private String tokenToConfirmEmail;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ShoppingCart cart = new ShoppingCart();
    @ManyToOne(fetch = FetchType.EAGER)
    private Role roles;
    @Transient
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singleton(roles));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String getName() {
        return email;
    }

    public boolean getAutoReg() {
        return autoReg;
    }

    public Long getId() {
        return id;
    }
}
