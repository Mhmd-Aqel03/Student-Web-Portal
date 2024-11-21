package com.example.Login.Repositries;

import com.example.Login.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByusername(String username);

    public User findUserById(int id);
}
