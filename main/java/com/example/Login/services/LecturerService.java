package com.example.Login.services;

import com.example.Login.DataTransferObjects.LecturerDTO;
import com.example.Login.Exceptions.NotFoundException;
import com.example.Login.Models.CClass;
import com.example.Login.Models.Lecturer;
import com.example.Login.Repositries.CClassRepo;
import com.example.Login.Repositries.LecturerRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class LecturerService {
    private final LecturerRepo lecturerRepo;
    private final CClassRepo classRepo;

    public List<Lecturer> getAllLecturers() {
        return lecturerRepo.findAll();
    }

    public Lecturer getLecturerById(int id) {
        Lecturer lecturer = lecturerRepo.findLecturerById(id);

        if (lecturer == null) {
            throw new NotFoundException("Lecturer not found");
        }

        return lecturer;
    }

    public void createLecturer(LecturerDTO requestBody) {
        Lecturer newLec = new Lecturer();
        System.out.println(requestBody);
        newLec.setHighestEducation(requestBody.getHighestEducation());
        newLec.setOfficeNumber(requestBody.getOfficeNumber());
        newLec.setName(requestBody.getName());

        lecturerRepo.save(newLec);
    }

    public void deleteLecturer(int id) {
        Lecturer lecturerToBeDeleted = lecturerRepo.findLecturerById(id);
        List<CClass> classes = classRepo.findAllByLecturerId(id);

        for(CClass c : classes) {
            c.setLecturer(null);

            classRepo.save(c);
        }

        if(lecturerToBeDeleted != null) {
            throw new NotFoundException("Lecturer not found");
        }

        lecturerRepo.deleteById(id);
    }

    public void updateLecturer(int id, LecturerDTO requestBody) {
        Lecturer updateLec = lecturerRepo.findLecturerById(id);

        if(updateLec != null) {
            throw new NotFoundException("Lecturer not found");
        }

        updateLec.setHighestEducation(requestBody.getHighestEducation());
        updateLec.setOfficeNumber(requestBody.getOfficeNumber());
        updateLec.setName(requestBody.getName());

        lecturerRepo.save(updateLec);
    }
}
