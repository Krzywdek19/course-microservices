package com.krzywdek19.student_service.student;

import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;

    @Override
    public URI save(Student student) throws URISyntaxException {
        if(!validateEmail(student.getEmail())){
            throw new StudentException(StudentError.EMAIL_IS_TAKEN);
        }
        var savedStudent = studentRepository.save(student);
        return new URI("/api/students/" + savedStudent.getId());
    }

    @Override
    public void update(Student s, Long id) {
         var student = studentRepository.findById(id).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
         if(shouldUpdate(s.getName(),student.getName())){
             student.setName(s.getName());
         }
        if(shouldUpdate(s.getLastname(),student.getLastname())){
            student.setLastname(s.getLastname());
        }
        if(shouldUpdate(s.getEmail(),student.getEmail())){
            if (!validateEmail(student.getEmail())) {
                throw new StudentException(StudentError.EMAIL_IS_TAKEN);
            }
            student.setEmail(s.getEmail());
        }
    }

    @Override
    public void delete(Long id) {
        changeStatus(id, StudentStatus.INACTIVE);
    }

    public void changeStatus(Long id, StudentStatus status){
        var student = studentRepository.findById(id).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        student.setStatus(status);
    }

    @Override
    public Student findById(Long id) {
        var student =  studentRepository.findById(id).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if(student.getStatus().equals(StudentStatus.INACTIVE)){
            throw new StudentException(StudentError.STUDENT_IS_INACTIVE);
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public List<Student> findAllByStatus(StudentStatus status){
        return studentRepository.findAllByStatus(status);
    }

    private boolean shouldUpdate(String newValue, String oldValue){
        return newValue != null && !newValue.isBlank() && !newValue.equals(oldValue);
    }

    private boolean validateEmail(String email){
        return !studentRepository.existsByEmail(email);
    }
}
