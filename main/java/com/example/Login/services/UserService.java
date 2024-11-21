package com.example.Login.services;

import com.example.Login.DataTransferObjects.StudentDTO;
import com.example.Login.Exceptions.BadRequestException;
import com.example.Login.Exceptions.NotFoundException;
import com.example.Login.Models.Role;
import com.example.Login.Models.Student;
import com.example.Login.Models.User;
import com.example.Login.Repositries.RoleRepo;
import com.example.Login.Repositries.StudentRepo;
import com.example.Login.Repositries.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final StudentRepo studentRepo;
    private final PasswordEncoder passwordEncoder;

    public void createStudentUser(StudentDTO studentDTO) {
        int curYear = Year.now().getValue();
        Random ran = new Random();

        //Create user
        User user = new User();
        user.setUsername(studentDTO.getUsername());
        user.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        user.setEmail(studentDTO.getEmail());
        user.setName(studentDTO.getName());

        //Create Role and link to user
        Role role = new Role();
        role.setName("STUDENT");
        roleRepo.save(role);
        user.setRole(role);

        //Create Student and link to user
        Student newStudent = new Student();
        newStudent.setMajor(studentDTO.getMajor());
        newStudent.setFaculty(studentDTO.getFaculty());
        newStudent.setEnrollmentYear(curYear);
        newStudent.setStudentNum(curYear + Integer.toString(ran.nextInt(0,10000)));
        user.setStudent(newStudent);
        studentRepo.save(newStudent);

        //Save User
        userRepo.save(user);
    }

    public User getUser(int id){
        User user = userRepo.findUserById(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }
}
