package com.example.Login.Auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


public class ApplicationUser implements UserDetails {
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final String username;
    private final String password;

    public ApplicationUser(Set<? extends GrantedAuthority> grantedAuthorities, String username, String password) {
        this.grantedAuthorities = grantedAuthorities;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
