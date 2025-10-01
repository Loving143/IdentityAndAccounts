package com.accounts.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.accounts.entity.Account;

public class CustomUserDetails implements UserDetails {

    private final Account account;

    public CustomUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (account == null || account.getUserRole() == null) {
            return new ArrayList<>();
        }

        return account.getUserRole().stream()
            .filter(ur -> ur.getRole() != null && ur.getRole().getName() != null)
            .map(ur -> new CustomGrantedAuthority(ur.getRole().getName()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return account != null ? account.getPasswordHash() : null;
    }

    @Override
    public String getUsername() {
        return account != null ? account.getEmail() : null;
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
        return true;
    }
}

