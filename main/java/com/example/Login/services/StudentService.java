package com.example.Login.services;

import com.example.Login.DataTransferObjects.StudentDTO;
import com.example.Login.Exceptions.NotFoundException;
import com.example.Login.Models.CClass;
import com.example.Login.Models.Course;
import com.example.Login.Models.Student;
import com.example.Login.Models.User;
import com.example.Login.Repositries.CClassRepo;
import com.example.Login.Repositries.StudentRepo;
import com.example.Login.Repositries.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class StudentService {
    private final UserService userService;
    private final ClassService classService;
    private final StudentRepo studentRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void signUpStudent(StudentDTO studentDTO) {
        userService.createStudentUser(studentDTO);
    }

    public List<CClass> getStudentClasses(String username) {
        Student student = getStudentByUsername(username);
//        System.out.println(username + " " + student);

        return student.getCClasses();
    }

    public void enrollClass(String username, int id){
        Student student = getStudentByUsername(username);

        CClass enrolledClass = classService.getClass(id);

        if(enrolledClass == null){
            throw new NotFoundException("Class to be enrolled not found");
        }

        student.getCClasses().add(enrolledClass);
        enrolledClass.getStudents().add(student);

        studentRepo.save(student);
    }

    public void removeClass(String username, int id){
        Student student = getStudentByUsername(username);

        CClass enrolledClass = classService.getClass(id);

        if(enrolledClass == null){
            throw new NotFoundException("Class to be removed not found");
        }

        student.getCClasses().remove(enrolledClass);

        studentRepo.save(student);
    }

    private Student getStudentByUsername(String username) {
        User user = userRepo.findByusername(username);

        if(user == null){
            throw new NotFoundException("user not found");
        }

        return user.getStudent();
    }

}
