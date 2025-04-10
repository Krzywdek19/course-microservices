package com.krzywdek19.courses.service;

import com.krzywdek19.courses.model.dto.EnrollmentStudent;
import com.krzywdek19.courses.model.dto.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "STUDENT-SERVICE", path = "/students")
public interface StudentServiceClient {
    @GetMapping("/{id}")
    Student getStudent(@PathVariable Long id);
    @GetMapping()
    EnrollmentStudent getStudentByEmail(@RequestParam String email);
}
