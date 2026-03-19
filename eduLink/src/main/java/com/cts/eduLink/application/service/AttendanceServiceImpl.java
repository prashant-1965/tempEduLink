package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.CourseException;
import com.cts.eduLink.application.classexception.StudentException;
import com.cts.eduLink.application.dto.AttendanceRegistrationDto;
import com.cts.eduLink.application.entity.Attendance;
import com.cts.eduLink.application.entity.Course;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.repository.AttendanceRepository;
import com.cts.eduLink.application.repository.CourseRepository;
import com.cts.eduLink.application.repository.StudentRepository;
import com.cts.eduLink.application.util.ClassSeparatorUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements IAttendanceService{

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public String registerAttendanceByStudentId(AttendanceRegistrationDto attendanceRegistrationDto) {
        Optional<Student> student = studentRepository.findStudentById(attendanceRegistrationDto.getStudentId());
        log.info("Attempting to register attendance for Student ID: {} in Course ID: {}", attendanceRegistrationDto.getStudentId(), attendanceRegistrationDto.getCourseId());
        if(student.isEmpty()){
            log.warn("Attendance registration failed: Student ID {} not found", attendanceRegistrationDto.getStudentId());
            throw new StudentException("Student is not register with id: "+attendanceRegistrationDto.getStudentId(), HttpStatus.NOT_FOUND);
        }
        Optional<Course> course = courseRepository.findCourseById(attendanceRegistrationDto.getCourseId());
        if(course.isEmpty()){
            log.warn("Attendance registration failed: Course ID {} not found", attendanceRegistrationDto.getCourseId());
            throw new CourseException("Course is not register with id: "+attendanceRegistrationDto.getCourseId(), HttpStatus.NOT_FOUND);
        }

        Optional<Student> student1 = studentRepository.checkStudentRegisteredcourse(attendanceRegistrationDto.getStudentId(), attendanceRegistrationDto.getCourseId());
        if(student1.isEmpty()){
            log.warn("Attendance registration failed: Student ID {} is not enrolled in Course ID {}", attendanceRegistrationDto.getStudentId(), attendanceRegistrationDto.getCourseId());
            throw new StudentException("Student is not registered in course id: "+attendanceRegistrationDto.getCourseId(),HttpStatus.NOT_FOUND);
        }
        Attendance attendance = ClassSeparatorUtils.attendanceDtoSeparator(attendanceRegistrationDto);
        attendance.setStudent(student.get());
        attendance.setCourse(course.get());
        attendanceRepository.save(attendance);
        log.info("Attendance successfully recorded for Student ID: {} in Course ID: {}", attendanceRegistrationDto.getStudentId(), attendanceRegistrationDto.getCourseId());
        return "Attendance recorded successFully!";
    }
}
