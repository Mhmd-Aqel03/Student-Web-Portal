package com.example.Login.Controllers;

import com.example.Login.Models.CClass;
import com.example.Login.services.AuthService;
import com.example.Login.services.ClassService;
import com.example.Login.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;
    private final AuthService authService;
    private final ClassService classService;

    @GetMapping(path = "/getAllClasses")
    public ResponseEntity<List<CClass>> getAllClasses() {
        try{
            List<CClass> classes = classService.getAllCLasses();

            return ResponseEntity.ok(classes);
        }catch (Exception e){
            throw new RuntimeException("Something went wrong with getting all classes: " + e.getMessage());
        }
    }

    @GetMapping(path = "/getStudentClasses")
    public ResponseEntity<?> getStudentClasses(){
        String username = authService.getAuthenticatedUsername();
        try {
            List<CClass> studentClasses = studentService.getStudentClasses(username);

            return ResponseEntity.ok(studentClasses);
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong with getting student classes" + e.getMessage());
        }

    }

    @PostMapping(path = "/enrollClass/{id}")
    public ResponseEntity<?> enrollClass(@PathVariable int id){
        String username = authService.getAuthenticatedUsername();
        try {
            studentService.enrollClass(username, id);

            return ResponseEntity.ok().body("Class enrolled Succefully");
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong with enrolling class" + e.getMessage());
        }
    }

    @PostMapping(path = "/removeClass/{id}")
    public ResponseEntity<?> removeCourse(@PathVariable int id){
        String username = authService.getAuthenticatedUsername();
        try {
            studentService.removeClass(username, id);

            return ResponseEntity.ok().body("Class Removed Succefully");
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong with removing course" + e.getMessage());
        }
    }
}
