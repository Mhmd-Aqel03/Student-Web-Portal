package com.example.Login;

import com.example.Login.Models.*;
import com.example.Login.Repositries.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class LoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

	@Bean
	public static CommandLineRunner commandLineRunner(
			UserRepo userRepo,
			RoleRepo roleRepo,
			LecturerRepo lecturerRepo,
			CourseRepo courseRepo,
			PasswordEncoder passwordEncoder,
			CClassRepo classRepo

	) {
		return args -> {
			User adminUser = new User();

			adminUser.setUsername("admin");
			adminUser.setPassword(passwordEncoder.encode("1234"));
			adminUser.setEmail("admin@gmail.com");
			adminUser.setName("MEEEEEEE");

			Role adminRole = new Role();
			adminRole.setName("ADMIN");

			adminUser.setRole(adminRole);

			userRepo.save(adminUser);

			Lecturer lecturer = new Lecturer();
			lecturer.setName("Mohammed");


			Course course = new Course();
			course.setName("C++");
			course.setDescription("Structured programming");



			Course course2 = new Course();
			course2.setName("Java");
			course2.setDescription("OOP");



			User studentUser = new User();
			Student student = new Student();

			studentUser.setUsername("anna");
			studentUser.setPassword(passwordEncoder.encode("1234"));
			studentUser.setEmail("student@gmail.com");
			studentUser.setName("AANNNNNAAAA");

			Role studentRole = new Role();
			studentRole.setName("STUDENT");
			studentUser.setRole(studentRole);

			userRepo.save(studentUser);

			CClass nclass = new CClass();

			nclass.setSection(1);
			nclass.setTimeFrame("9-11");
			nclass.setLecturer(lecturer);
			nclass.setCourse(course);



			CClass nclass2 = new CClass();
			nclass2.setSection(1);
			nclass2.setTimeFrame("10-12");
			nclass2.setLecturer(lecturer);
			nclass2.setCourse(course2);

//			classRepo.save(nclass);
//			classRepo.save(nclass2);
			courseRepo.save(course);
			courseRepo.save(course2);
			lecturerRepo.save(lecturer);

		};
	}
}
