package com.example.Login.Repositries;

import com.example.Login.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    Course findCourseById(int id);

    void deleteCourseById(int id);
}
