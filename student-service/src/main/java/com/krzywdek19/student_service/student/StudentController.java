package com.krzywdek19.student_service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam Optional<StudentStatus> status){
        return status.map(studentStatus -> ResponseEntity.ok(studentService.findAllByStatus(studentStatus))).orElseGet(() -> ResponseEntity.ok(studentService.findAll()));
    }

    @PostMapping
    public ResponseEntity<URI> createStudent(@RequestBody Student student) throws URISyntaxException {
        var savedStudent = studentService.save(student);
        return ResponseEntity.created(savedStudent).body(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody Student student){
        studentService.update(student, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id){
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> changeStudentStatus(@PathVariable Long id, @RequestBody StudentStatus status){
        studentService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

}
