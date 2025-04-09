package com.krzywdek19.courses.controller;

import com.krzywdek19.courses.model.Course;
import com.krzywdek19.courses.model.CourseMember;
import com.krzywdek19.courses.model.dto.Student;
import com.krzywdek19.courses.service.CourseService;
import com.krzywdek19.courses.service.StudentServiceClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/courses")
@RestController
public class CourseController {
    private final CourseService courseService;
    private final StudentServiceClient studentService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false)Course.Status status) {
        if (status == null) {
            return ResponseEntity.ok(courseService.findAll());
        }
        return ResponseEntity.ok(courseService.findAllByStatus(status));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Course> getCourse(@PathVariable String code){
        return ResponseEntity.ok(courseService.getCourse(code));
    }

    @PostMapping
    public ResponseEntity<URI> createCourse(@RequestBody @Valid Course course) throws URISyntaxException {
        var URI = courseService.addCourse(course);
        return ResponseEntity.created(URI).body(URI);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Void> updateCourse(@PathVariable String code,@RequestBody @Valid Course course) {
        courseService.updateCourse(code,course);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Void> changeStatus(@PathVariable String code, @RequestBody Course.Status status) {
        courseService.changeStatus(code, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> changeStatus(@PathVariable String code) {
        courseService.deleteCourse(code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{code}")
    public ResponseEntity<Void> signStudentToCourse(@PathVariable String code, @RequestBody @Valid Long studentId) {
        courseService.signStudentToCourse(studentId, code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{code}/members")
    public ResponseEntity<List<CourseMember>> getCourseMembers(@PathVariable String code) {
        return ResponseEntity.ok(courseService.getCourseMembers(code));
    }
}
