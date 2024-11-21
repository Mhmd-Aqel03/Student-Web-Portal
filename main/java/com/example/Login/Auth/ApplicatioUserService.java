package com.example.Login.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicatioUserService implements UserDetailsService {
    private final ApplicationUserDAO applicationUserDAO;
    @Autowired
    public ApplicatioUserService(@Qualifier("Maria") ApplicationUserDAO applicationUserDAO) {
        this.applicationUserDAO = applicationUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDAO.selectApplicationUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }


}
