package com.example.Login.services;

import com.example.Login.DataTransferObjects.ClassDTO;
import com.example.Login.Exceptions.NotFoundException;
import com.example.Login.Models.CClass;
import com.example.Login.Models.Course;
import com.example.Login.Models.Lecturer;
import com.example.Login.Repositries.CClassRepo;
import com.example.Login.Repositries.CourseRepo;
import com.example.Login.Repositries.LecturerRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ClassService {
    private final LecturerRepo lecturerRepo;
    private final CourseRepo courseRepo;
    private final CClassRepo classRepo;

    public List<CClass> getAllCLasses() {
        return classRepo.findAll();
    }

    public CClass getClass(int id) {
        CClass cclass = classRepo.findCClassById(id);

        if(cclass == null) {
            throw new NotFoundException("Class not found");
        }

        return cclass;
    }

    public void createClass(ClassDTO requestBody) {
        CClass newClass = new CClass();
        newClass.setSection(requestBody.getSection());
        newClass.setTimeFrame(requestBody.getTimeFrame());

        Course course = courseRepo.findCourseById(Integer.valueOf(requestBody.getCourse_id()));
        Lecturer lecturer = lecturerRepo.findLecturerById(Integer.valueOf(requestBody.getLecturer_id()));

        newClass.setCourse(course);
        newClass.setLecturer(lecturer);

        classRepo.save(newClass);
    }

    public void updateClass(ClassDTO requestBody, int id) {
        CClass updateClass = classRepo.findCClassById(id);

        if(updateClass == null) {
            throw new NotFoundException("Class not found");
        }

        updateClass.setSection(requestBody.getSection());
        updateClass.setTimeFrame(requestBody.getTimeFrame());

        Course course = courseRepo.findCourseById(Integer.valueOf(requestBody.getCourse_id()));
        Lecturer lecturer = lecturerRepo.findLecturerById(Integer.valueOf(requestBody.getLecturer_id()));

        updateClass.setCourse(course);
        updateClass.setLecturer(lecturer);

        classRepo.save(updateClass);
    }

    public void deleteClass(int id) {
        CClass cclass = classRepo.findCClassById(id);

        if(cclass == null) {
            throw new NotFoundException("Class not found");
        }

        classRepo.deleteById(id);
    }
}
