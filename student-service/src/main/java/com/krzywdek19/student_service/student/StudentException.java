package com.krzywdek19.student_service.student;

import lombok.Getter;

@Getter
public class StudentException extends RuntimeException {
  private final StudentError studentError;

  public StudentException(StudentError studentError) {
    super(studentError.getMessage());
    this.studentError = studentError;
  }
}
