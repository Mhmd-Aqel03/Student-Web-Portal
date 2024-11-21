package com.example.Login.Controllers;

import com.example.Login.DataTransferObjects.LecturerDTO;
import com.example.Login.Exceptions.BadRequestException;
import com.example.Login.Models.Lecturer;
import com.example.Login.services.LecturerService;
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
public class LecturerController {
    LecturerService lecturerService;
    @GetMapping(path = "/getAllLecturers")
    public ResponseEntity<?> getAllLecturers() {
        try {
            List<Lecturer> lecturers = lecturerService.getAllLecturers();

            return ResponseEntity.status(HttpStatus.OK).body(lecturers);
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong with getting all lecturers: " + e.getMessage());
        }
    }

    @GetMapping(path = "/getLecturer/{id}")
    public ResponseEntity<?> getLecturerById(@PathVariable int id) {
        try {
            Lecturer lec = lecturerService.getLecturerById(id);

            return ResponseEntity.status(HttpStatus.OK).body(lec);
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong with getting lecturer: " + e.getMessage());
        }
    }

    @PostMapping(path = "/createLecturer")
    public ResponseEntity<?> createLec(@RequestBody LecturerDTO requestBody) {
        if (Stream.of(requestBody.getName(), requestBody.getHighestEducation(),requestBody.getOfficeNumber())
                .anyMatch(Objects::isNull)) {
            throw new BadRequestException("Not All fields entered");
        }

        try {
            lecturerService.createLecturer(requestBody);

            return ResponseEntity.status(HttpStatus.OK).body("Lecturer created succesfully");
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong with creating lecturer: " + e.getMessage());
        }
    }

    @PostMapping(path = "deleteLec/{id}")
    public ResponseEntity<?> deleteLec(@PathVariable int id) {
       try{
           lecturerService.deleteLecturer(id);

           return ResponseEntity.status(HttpStatus.OK).body("Lecturer deleted succesfully");
       }catch (RuntimeException e){
           throw new RuntimeException("Something went wrong with deleting lecturer: " + e.getMessage());
       }
    }

    @PostMapping(path = "updateLec/{id}")
    public ResponseEntity<?> createLec(@PathVariable int id, @RequestBody LecturerDTO requestBody) {
        if (Stream.of(requestBody.getName(), requestBody.getHighestEducation(),requestBody.getOfficeNumber())
                .anyMatch(Objects::isNull)) {
            throw new BadRequestException("Not All fields entered");
        }

        try{
            lecturerService.updateLecturer(id, requestBody);

            return ResponseEntity.status(HttpStatus.OK).body("Lecturer Updated succesfully");
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong with updating lecturer: " + e.getMessage());
        }
    }
}
