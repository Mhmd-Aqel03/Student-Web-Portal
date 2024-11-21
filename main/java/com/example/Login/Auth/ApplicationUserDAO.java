package com.example.Login.Auth;

import java.util.Optional;

public interface ApplicationUserDAO {
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
