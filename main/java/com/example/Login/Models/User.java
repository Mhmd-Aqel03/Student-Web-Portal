package com.example.Login.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String name;
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    Student student;
}
