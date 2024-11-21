package com.example.Login.Repositries;

import com.example.Login.Models.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepo extends JpaRepository<Lecturer, Integer> {
    Lecturer findLecturerById(int id);


}
