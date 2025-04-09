package com.krzywdek19.courses.controller;

import com.krzywdek19.courses.exceptions.CourseError;
import com.krzywdek19.courses.exceptions.CourseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(value = CourseException.class)
    public ResponseEntity<?> handleCourseException(CourseException e){
        System.err.println(e.getMessage());
        if(e.getCourseError().equals(CourseError.COURSE_NOT_FOUND)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        if(e.getCourseError().equals(CourseError.COURSE_CAN_NOT_SET_FULL_STATUS) ||
                e.getCourseError().equals(CourseError.STUDENT_IS_ALREADY_ENROLLED) ||
        e.getCourseError().equals(CourseError.COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
