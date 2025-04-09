package com.krzywdek19.courses.model;

import com.krzywdek19.courses.exceptions.CourseError;
import com.krzywdek19.courses.exceptions.CourseException;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Course {
    @Id
    private String code;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Future
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;
    private Long participantsLimit;
    @NotNull
    @Min(0)
    private Long participantsNumber;
    @NotNull
    private Status status;
    private List<CourseMember> courseMembers = new ArrayList<>();

    public void addCourseMember(CourseMember courseMember) {
        courseMembers.add(courseMember);
        participantsNumber = (long) courseMembers.size();
    }

    private void validateCourseDate(){
        if(startDate.isAfter(endDate)){
            throw new CourseException(CourseError.COURSE_START_DATE_IS_AFTER_END_DATE);
        }
    }

    private void validateParticipants(){
        if(participantsNumber > participantsLimit){
            throw new CourseException(CourseError.COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED);
        }
    }

    private void validateStatus(){
        if(Status.FULL.equals(status) && !participantsNumber.equals(participantsLimit)){
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_FULL_STATUS);
        }
        if(Status.ACTIVE.equals(status) && participantsNumber.equals(participantsLimit)){
            throw new CourseException(CourseError.COURSE_CAN_NOT_SET_ACTIVE_STATUS);
        }
    }

    public void validateCourse(){
        validateParticipants();
        validateStatus();
        validateCourseDate();
    }

    public enum Status {
        ACTIVE, INACTIVE, FULL;
    }
}
