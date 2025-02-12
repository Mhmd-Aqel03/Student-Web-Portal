package com.example.Login.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String studentNum;
    private int enrollmentYear;
    private String faculty;
    private String major;

    @ManyToMany(mappedBy = "students")
    private List<CClass> CClasses;
}
