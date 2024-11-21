package com.example.Login.services;

import com.example.Login.DataTransferObjects.CourseDTO;
import com.example.Login.Exceptions.NotFoundException;
import com.example.Login.Models.Course;
import com.example.Login.Repositries.CClassRepo;
import com.example.Login.Repositries.CourseRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;
    private final CClassRepo classRepo;

    public List<Course> getAllCourses(){
        return courseRepo.findAll();
    }

    public Course getCourseById(int id){
        Course course = courseRepo.findCourseById(id);

        if(course == null){
            throw new NotFoundException("Course Not Found");
        }

        return course;
    }

    public void createCourse(CourseDTO requestBody){
        Course newCourse = new Course();
        newCourse.setName(requestBody.getName());
        newCourse.setDescription(requestBody.getDescription());

        courseRepo.save(newCourse);
    }

    public void deleteCourse(int id){
        Course course = courseRepo.findCourseById(id);

        if(course == null){
            throw new NotFoundException("Course Not Found");
        }

        classRepo.deleteAllByCourseId(id);

        courseRepo.deleteCourseById(id);
    }

    public void updateCourse(int id, CourseDTO requestBody){
        Course course = courseRepo.findCourseById(id);

        if(course == null){
            throw new NotFoundException("Course Not Found");
        }

        course.setName(requestBody.getName());
        course.setDescription(requestBody.getDescription());

        courseRepo.save(course);
    }
}
