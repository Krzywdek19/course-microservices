package com.krzywdek19.courses.service;

import com.krzywdek19.courses.model.Course;
import com.krzywdek19.courses.model.CourseMember;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface CourseService {
    List<Course> findAll();
    List<Course> findAllByStatus(Course.Status status);
    Course getCourse(String code);
    URI addCourse(Course course) throws URISyntaxException;
    void updateCourse(String code, Course course);
    void deleteCourse(String code);
    void changeStatus(String code, Course.Status status);
    void signStudentToCourse(Long studentCode, String courseCode);
    List<CourseMember> getCourseMembers(String courseCode);
}
