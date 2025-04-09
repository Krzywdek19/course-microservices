package com.krzywdek19.courses.exceptions;

import lombok.Getter;

@Getter
public enum CourseError {
    COURSE_NOT_FOUND("Cannot found this course"),
    COURSE_NOT_ACTIVE("Course is not active"),
    COURSE_ALREADY_EXIST("This course already exist"),
    COURSE_START_DATE_IS_AFTER_END_DATE("Start date is after end date"),
    COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED("Course participant limit is exceeded"),
    COURSE_CAN_NOT_SET_FULL_STATUS("Cannot set full status"),
    COURSE_CAN_NOT_SET_ACTIVE_STATUS("Cannot set active status"),
    STUDENT_IS_ALREADY_ENROLLED("Student is already enrolled to this course");
    private final String message;
    CourseError(String message) {
        this.message = message;
    }
}
