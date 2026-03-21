package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.CourseException;
import com.cts.eduLink.application.classexception.StudentException;
import com.cts.eduLink.application.dto.AttendanceRegistrationDto;
import com.cts.eduLink.application.entity.Attendance;
import com.cts.eduLink.application.entity.Course;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.projection.CourseAttendanceProjection;
import com.cts.eduLink.application.projection.CourseSummaryProjection;
import com.cts.eduLink.application.repository.AttendanceRepository;
import com.cts.eduLink.application.repository.CourseRepository;
import com.cts.eduLink.application.repository.StudentRepository;
import com.cts.eduLink.application.util.AttendanceCalculator;
import com.cts.eduLink.application.util.DtoMapper;
import com.cts.eduLink.application.util.DateUtils;
import com.cts.eduLink.application.util.ProjectionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements IAttendanceService{

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    @Transactional
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
        Attendance attendance = DtoMapper.attendanceDtoSeparator(attendanceRegistrationDto);
        attendance.setStudent(student.get());
        attendance.setCourse(course.get());
        attendanceRepository.save(attendance);
        log.info("Attendance successfully recorded for Student ID: {} in Course ID: {}", attendanceRegistrationDto.getStudentId(), attendanceRegistrationDto.getCourseId());
        return "Attendance recorded successFully!";
    }

    @Override
    public List<CourseAttendanceProjection> findAttendanceByCourse(Long studentId) {
        log.info("Fetching attendance report for Student ID: {}", studentId);
        List<CourseSummaryProjection> courseSummaryProjections = courseRepository.findCourseSummaryListByStudentId(studentId);
        if(courseSummaryProjections.isEmpty()){
            log.warn("Attendance request failed: Student {} has no course registrations.", studentId);
            throw new CourseException(studentId+" is not registered into any course",HttpStatus.NOT_FOUND);
        }
        log.debug("Found {} courses for student {}", courseSummaryProjections.size(), studentId);
        List<CourseAttendanceProjection> courseAttendanceProjections = new ArrayList<>();
        for(CourseSummaryProjection course: courseSummaryProjections){
            Long totalAttendedDays = attendanceRepository.countAttendanceByIdAndStudentId(course.getId(),studentId);
            if(totalAttendedDays >0L){
                LocalDateTime firstAttendanceDate = attendanceRepository.findFirstEnrollmentDate(course.getCourseId(),studentId);
                LocalDateTime lastAttendanceDate = attendanceRepository.findLastEnrollmentDate(course.getCourseId(),studentId);
                Long daysBetween = DateUtils.getCalendarDaysBetween(firstAttendanceDate,lastAttendanceDate);
                double attendancePercentage = AttendanceCalculator.calculateAttendance(totalAttendedDays,daysBetween);
                log.info("Course: {} | Attended: {} days | first Attended date: {} | last attended date: {} | Window: {} days | Rate: {}%",course.getCourseId(), totalAttendedDays, firstAttendanceDate,lastAttendanceDate, daysBetween, String.format("%.2f", attendancePercentage));
                CourseAttendanceProjection courseAttendanceProjection = ProjectionMapper.courseAttendanceProjection(course,attendancePercentage);
                courseAttendanceProjections.add(courseAttendanceProjection);
            }else{
                log.debug("Skipping Course {}: Zero attendance records found.", course.getCourseId());
            }
        }
        log.info("Completed attendance report for Student {}. Courses processed: {}", studentId, courseAttendanceProjections.size());
        return courseAttendanceProjections;
    }
}
