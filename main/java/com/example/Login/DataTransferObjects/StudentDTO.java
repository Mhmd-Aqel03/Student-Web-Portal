package com.example.Login.DataTransferObjects;

import lombok.Data;
@Data
public class StudentDTO {
    private String username;
    private String password;
    private String email;
    private String name;
    private String faculty;
    private String major;
}
