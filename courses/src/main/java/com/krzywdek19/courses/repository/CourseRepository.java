package com.krzywdek19.courses.repository;

import com.krzywdek19.courses.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    boolean existsByCode(String code);
    List<Course> findByStatus(Course.Status status);
}
