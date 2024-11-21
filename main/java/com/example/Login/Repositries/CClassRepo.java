package com.example.Login.Repositries;

import com.example.Login.Models.CClass;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CClassRepo extends JpaRepository<CClass,Integer> {
    CClass findCClassById(int id);

    @Transactional
    void deleteAllByCourseId(int id);

    List<CClass> findAllByLecturerId(int id);

}
