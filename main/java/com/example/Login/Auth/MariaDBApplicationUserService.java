package com.example.Login.Auth;

import com.example.Login.Models.User;
import com.example.Login.Repositries.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("Maria")
public class MariaDBApplicationUserService implements ApplicationUserDAO {
    private UserRepo userRepo;

    @Autowired
    public MariaDBApplicationUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        User user = userRepo.findByusername(username);
        if(user == null) {
            return Optional.empty();
        }
        ApplicationUser applicationUser = new ApplicationUser(getGrantedAuthority(user), user.getUsername(),user.getPassword());
        return Optional.of(applicationUser);
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority(User user) {
        Set<SimpleGrantedAuthority> permessions = new HashSet<>();

        permessions.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        return permessions;
    }
}
