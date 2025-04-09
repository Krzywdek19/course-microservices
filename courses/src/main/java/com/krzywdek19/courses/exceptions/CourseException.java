package com.krzywdek19.courses.exceptions;

import lombok.Getter;

@Getter
public class CourseException extends RuntimeException {
  private final CourseError courseError;
    public CourseException(CourseError c) {
        super(c.getMessage());
        courseError = c;
    }
}
