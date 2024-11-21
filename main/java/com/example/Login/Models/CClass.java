package com.example.Login.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class CClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int section;
    private String timeFrame;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @ManyToMany
    @JoinTable(
            name = "student_class",
            joinColumns = {
                    @JoinColumn(name = "class_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "student_id")
            }
    )
    @JsonIgnore
    private List<Student> students;
}
