package com.krzywdek19.courses.service;

import com.krzywdek19.courses.exceptions.CourseError;
import com.krzywdek19.courses.exceptions.CourseException;
import com.krzywdek19.courses.mapper.CourseMapper;
import com.krzywdek19.courses.model.Course;
import com.krzywdek19.courses.model.CourseMember;
import com.krzywdek19.courses.model.dto.EnrollmentStudent;
import com.krzywdek19.courses.repository.CourseRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentServiceClient studentServiceClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAllByStatus(Course.Status status) {
        return courseRepository.findByStatus(status);
    }

    @Override
    public Course getCourse(String code) {
        return courseRepository.findById(code).orElseThrow(()-> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public URI addCourse(Course course) throws URISyntaxException {
        course.validateCourse();
        if(courseRepository.existsByCode(course.getCode())){
            throw new CourseException(CourseError.COURSE_ALREADY_EXIST);
        }
        return new URI("/api/courses/" + courseRepository.save(course).getCode());
    }

    @Override
    public void updateCourse(String code, Course c) {
        var course = getCourse(code);
        courseMapper.updateCourse(course, c);
        course.validateCourse();
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String code) {
        var course = getCourse(code);
        course.setStatus(Course.Status.INACTIVE);
        courseRepository.save(course);

    }

    @Override
    public void changeStatus(String code, Course.Status status) {
        var course = getCourse(code);
        course.setStatus(status);
        course.validateCourse();
        courseRepository.save(course);
    }

    @Override
    public void signStudentToCourse(Long studentCode, String courseCode) {
        var course = getCourse(courseCode);
        if(course.getStatus() != Course.Status.ACTIVE){
            throw new CourseException(CourseError.COURSE_NOT_ACTIVE);
        }
        var student = studentServiceClient.getStudent(studentCode);
        course.getCourseMembers().forEach(courseMember->{
            if(courseMember.getEmail().equals(student.getEmail())){
                throw new CourseException(CourseError.STUDENT_IS_ALREADY_ENROLLED);
            }
        });
        if(!student.getStatus().equals("ACTIVE")){
            throw new BadRequestException("Student is not active");
        }
        course.addCourseMember(new CourseMember(student.getEmail()));
        if(Objects.equals(course.getParticipantsNumber(), course.getParticipantsLimit())){
            course.setStatus(Course.Status.FULL);
        }
        courseRepository.save(course);
    }

    @Override
    public List<CourseMember> getCourseMembers(String courseCode) {
        var course = courseRepository.findById(courseCode).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
        return course.getCourseMembers();
    }

    @Override
    public List<EnrollmentStudent> getEnrollmentStudents(String courseCode) {
        var course = getCourse(courseCode);
        List<EnrollmentStudent> students = new ArrayList<>();
        course.getCourseMembers().forEach(courseMember->{
            students.add(studentServiceClient.getStudentByEmail(courseMember.getEmail()));
        });
        return students;
    }

    @Override
    public void finishEnroll(String courseCode) {
        var course =  getCourse(courseCode);
        course.setStatus(Course.Status.INACTIVE);
        courseRepository.save(course);
        var enrolledStudents = getEnrollmentStudents(courseCode);
        enrolledStudents.forEach(student->{
            rabbitTemplate.convertAndSend("notification",student);
        });
    }
}
