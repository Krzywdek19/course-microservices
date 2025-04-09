package com.krzywdek19.student_service.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler(value = StudentException.class)
    public ResponseEntity<?> handleException(StudentException ex) {
        if(ex.getStudentError().equals(StudentError.EMAIL_IS_TAKEN)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        if(ex.getStudentError().equals(StudentError.STUDENT_IS_INACTIVE)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getStudentError().getMessage());
    }
}
