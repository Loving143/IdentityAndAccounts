package com.accounts.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.accounts.entity.Account;

public class CustomUserDetails implements UserDetails {

    private final Account account;

    public CustomUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<CustomGrantedAuthority>authorities = new ArrayList<>();
    	CustomGrantedAuthority authority = new CustomGrantedAuthority("ROLE_USER");
    	authorities.add(authority);
    	return authorities;
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

