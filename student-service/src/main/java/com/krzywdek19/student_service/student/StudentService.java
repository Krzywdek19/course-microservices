package com.krzywdek19.student_service.student;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface StudentService {
    URI save(Student student) throws URISyntaxException;
    void update(Student student, Long id);
    void delete(Long id);
    Student findById(Long id);
    Student findByEmail(String email);
    List<Student> findAll();
    List<Student> findAllByStatus(StudentStatus status);
    void changeStatus(Long id, StudentStatus status);
}
