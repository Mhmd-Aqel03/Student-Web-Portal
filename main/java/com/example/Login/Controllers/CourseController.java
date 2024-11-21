package com.example.Login.Controllers;

import com.example.Login.DataTransferObjects.CourseDTO;
import com.example.Login.Exceptions.BadRequestException;
import com.example.Login.Models.Course;
import com.example.Login.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/admin")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping(path = "getAllCourses")
    public ResponseEntity<?> getAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();

            return ResponseEntity.ok(courses);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in getting all courses: " + e.getMessage());
        }
    }

    @GetMapping(path = "getCourse/{id}")
    public ResponseEntity<?> getCourse(@PathVariable int id) {
        try {
            Course course = courseService.getCourseById(id);

            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in getting course: " + e.getMessage());
        }
    }

    @PostMapping(path = "createCourse")
    public ResponseEntity<?> createCourPost(@RequestBody CourseDTO requestBody) {
        if (Stream.of(requestBody.getDescription(),requestBody.getName())
                .anyMatch(Objects::isNull)) {
            throw new BadRequestException("Not All fields entered");
        }

        try {
            courseService.createCourse(requestBody);

            return ResponseEntity.status(HttpStatus.CREATED).body("Course created");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in creating course: " + e.getMessage());
        }
    }

    @PostMapping(path = "/deleteCour/{id}")
    public ResponseEntity<?> deleteCour(@PathVariable int id) {
        try {
            courseService.deleteCourse(id);

            return ResponseEntity.status(HttpStatus.OK).body("Course deleted");
        } catch (Exception e) {
            throw new RuntimeException("Error in deleting course: " + e.getMessage());
        }
    }

    @PostMapping(path = "updateCour/{id}")
    public ResponseEntity<?> updateCour(@PathVariable int id, @RequestBody CourseDTO requestBody) {
        if (Stream.of(requestBody.getDescription(),requestBody.getName())
                .anyMatch(Objects::isNull)) {
            throw new BadRequestException("Not All fields entered");
        }

        try {
            courseService.updateCourse(id, requestBody);

            return ResponseEntity.status(HttpStatus.OK).body("Course updated");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in updating course: " + e.getMessage());
        }
    }
}
