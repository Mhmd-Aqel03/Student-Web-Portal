package com.example.Login.Controllers;

import com.example.Login.DataTransferObjects.StudentDTO;
import com.example.Login.Exceptions.BadRequestException;
import com.example.Login.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
public class UserController {
    private StudentService studentService;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signUp(@RequestBody StudentDTO studentDTO) {
        if (Stream.of(studentDTO.getUsername(), studentDTO.getPassword(), studentDTO.getEmail(), studentDTO.getName())
                .anyMatch(Objects::isNull)) {
            throw new BadRequestException("Not All user fields entered");
        }

        try {
            studentService.signUpStudent(studentDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Student Successfully Signed Up");
        }catch (RuntimeException e){
            throw new RuntimeException("Something went wrong with user sign up: " + e.getMessage());
        }
    }

}
