package com.krzywdek19.student_service;

import com.krzywdek19.student_service.student.Student;
import com.krzywdek19.student_service.student.StudentService;
import com.krzywdek19.student_service.student.StudentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
public class StudentServiceApplication implements CommandLineRunner {
	private final StudentService studentService;
	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		studentService.save(new Student(null,"Jakub","Krzywdzinski","krzywdek19@wp.pl", StudentStatus.ACTIVE));
		studentService.save(new Student(null,"Kacper","Nowak","nowak@wp.pl", StudentStatus.ACTIVE));
		studentService.save(new Student(null,"Jan","Kowalski","kowal@wp.pl",StudentStatus.INACTIVE));
		studentService.save(new Student(null,"Will","Smith","smith@wp.pl",StudentStatus.INACTIVE));
		studentService.save(new Student(null,"Szymon","Keja","pirat@wp.pl",StudentStatus.ACTIVE));
	}
}
