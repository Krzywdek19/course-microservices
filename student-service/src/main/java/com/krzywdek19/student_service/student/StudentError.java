package com.krzywdek19.student_service.student;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum StudentError {
    STUDENT_NOT_FOUND("Student does not exist"),
    EMAIL_IS_TAKEN("Student with this email already exists"),
    STUDENT_IS_INACTIVE("Student is inactive");


    private final String message;

    StudentError(String message) {
        this.message = message;
    }
}
