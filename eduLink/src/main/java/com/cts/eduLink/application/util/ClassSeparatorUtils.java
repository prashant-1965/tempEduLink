package com.cts.eduLink.application.util;

import com.cts.eduLink.application.dto.*;
import com.cts.eduLink.application.entity.*;

import java.time.LocalDateTime;

public class ClassSeparatorUtils {

    public static Student studentDtoSeparator(StudentRegistrationDto studentDto){
        Student student = new Student();
        student.setStudentAddress(studentDto.getStudentAddress());
        student.setStudentDOB(studentDto.getStudentDOB());
        student.setStudentGender(studentDto.getStudentGender());
        student.setStudentEnrollmentDateTime(LocalDateTime.now());
        Long studentId = UIDGeneratorUtils.uidGenerator();
        student.setStudentId(studentId);
        return student;
    }
    public static AppUser appUserDtoSeparator(StudentRegistrationDto appUserDto){
        AppUser appUser = new AppUser();
        appUser.setUserEmail(appUserDto.getUserEmail());
        appUser.setUserName(appUserDto.getUserName());
        appUser.setPhoneNumber(appUserDto.getPhoneNumber());
        return appUser;
    }

    public static Course facultyDtoSeparator(CourseRegistrationDto courseRegistrationDto){
        Course course = new Course();
        course.setCourseTitle(courseRegistrationDto.getCourseTitle());
        course.setCourseSubject(courseRegistrationDto.getCourseSubject());
        course.setCourseCredit(courseRegistrationDto.getCourseCredit());
        course.setCourseRating(0.0);
        course.setTotalCourseRatingCount(0L);
        course.setCourseGradeLevel(courseRegistrationDto.getCourseGradeLevel());
        Long courseId = UIDGeneratorUtils.uidGenerator();
        course.setCourseId(courseId);
        return course;
    }

    public static Faculty facultyDtoSeparator(FacultyRegistrationDto facultyRegistrationDto){
        Faculty faculty = new Faculty();
        faculty.setFacultyGender(facultyRegistrationDto.getFacultyGender());
        faculty.setFacultyYearOfExperience(facultyRegistrationDto.getFacultyYearOfExperience());
        faculty.setFacultyAddress(facultyRegistrationDto.getFacultyAddress());
        faculty.setFacultyRating(0.0);
        faculty.setTotalFacultyRatingCount(0L);
        Long facultyId = UIDGeneratorUtils.uidGenerator();
        faculty.setFacultyId(facultyId);
        return faculty;
    }
    public static AppUser appUserDtoSeparator(FacultyRegistrationDto facultyRegistrationDto){
        AppUser appUser = new AppUser();
        appUser.setUserName(facultyRegistrationDto.getUserName());
        appUser.setUserEmail(facultyRegistrationDto.getUserEmail());
        appUser.setPhoneNumber(facultyRegistrationDto.getPhoneNumber());
        return appUser;
    }

    public static FeedBack feedBackDtoSeparator(FeedbackDto feedbackDto){
        FeedBack feedBack = new FeedBack();
        feedBack.setMessage(feedbackDto.getComment());
        feedBack.setRating(feedbackDto.getRating());
        return feedBack;
    }

    public static Exam examDtoSeparator(ExamRegistrationDto examRegistrationDto){
        Exam exam = new Exam();
        exam.setExamName(examRegistrationDto.getExamName());
        exam.setExamStatus("ACTIVE");
        exam.setExamLocalDateTime(LocalDateTime.now());
        Long examId = UIDGeneratorUtils.uidGenerator();
        exam.setExamId(examId);
        return exam;
    }

    public static Grade gradeDtoSeparator(GradeRegistrationDto gradeRegistrationDto){
        Grade grade = new Grade();
        grade.setScore(gradeRegistrationDto.getScore());
        grade.setStatus("COMPLETED");
        String studentGrade = GradeCalculator.calculateGrade(gradeRegistrationDto.getScore());
        grade.setGrade(studentGrade);
        Long gradeId = UIDGeneratorUtils.uidGenerator();
        grade.setGradeId(gradeId);
        return  grade;
    }

    public static Attendance attendanceDtoSeparator(AttendanceRegistrationDto attendanceRegistrationDto){
        Attendance attendance = new Attendance();
        attendance.setLocalDateTime(LocalDateTime.now());
        return attendance;
    }
}
