package com.krzywdek19.student_service.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByEmail(String email);
    List<Student> findAllByStatus(StudentStatus status);
    Optional<Student> findByEmail(String email);
}
