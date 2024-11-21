package com.example.Login.Controllers;

import com.example.Login.DataTransferObjects.ClassDTO;
import com.example.Login.Exceptions.BadRequestException;
import com.example.Login.Models.CClass;
import com.example.Login.Models.Course;
import com.example.Login.Models.Lecturer;
import com.example.Login.Repositries.CClassRepo;
import com.example.Login.Repositries.CourseRepo;
import com.example.Login.Repositries.LecturerRepo;
import com.example.Login.services.ClassService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class ClassController {
    ClassService classService;

    @GetMapping(path = "getAllClasses")
    public ResponseEntity<?> getAllCourses() {
        try {
            List<CClass> allCLasses = classService.getAllCLasses();

            return ResponseEntity.status(HttpStatus.OK).body(allCLasses);
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong with getting classes: " + e.getMessage());
        }
    }
    @GetMapping(path = "getClass/{id}")
    public ResponseEntity<?> getClass(@PathVariable int id) {
        try {
            CClass Class = classService.getClass(id);

            return ResponseEntity.status(HttpStatus.OK).body(Class);
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong with getting class: " + e.getMessage());
        }
    }

    @PostMapping(path = "/createClass")
    public ResponseEntity<?> createClass(@RequestBody ClassDTO requestBody) {
        if (Stream.of(requestBody.getSection(), requestBody.getTimeFrame(), requestBody.getCourse_id(),requestBody.getLecturer_id())
                .anyMatch(Objects::isNull)) {
            throw new BadRequestException("Not All fields entered");
        }

        try {
            classService.createClass(requestBody);

            return ResponseEntity.status(HttpStatus.CREATED).body("Class created");
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong with creating class: " + e.getMessage());
        }
    }

    @PostMapping(path = "/updateClass/{id}")
    public ResponseEntity<?> updateClass(@RequestBody ClassDTO requestBody, @PathVariable int id) {
        if (Stream.of(requestBody.getSection(), requestBody.getTimeFrame(), requestBody.getCourse_id(),requestBody.getLecturer_id())
                .anyMatch(Objects::isNull)) {
            throw new BadRequestException("Not All fields entered");
        }

        try {
            classService.updateClass(requestBody,id);

            return ResponseEntity.status(HttpStatus.CREATED).body("Class Updated");
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong with updating class: " + e.getMessage());
        }
    }

    @PostMapping(path = "deleteClass/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable int id){
        try {
            classService.deleteClass(id);

            return ResponseEntity.status(HttpStatus.CREATED).body("Class Deleted");
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong with deleting class: " + e.getMessage());
        }
    }
}
