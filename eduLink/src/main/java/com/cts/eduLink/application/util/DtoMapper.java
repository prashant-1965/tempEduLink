package com.cts.eduLink.application.util;

import com.cts.eduLink.application.dto.*;
import com.cts.eduLink.application.entity.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class DtoMapper {

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
    public static AppUser appUserDtoSeparator(StudentRegistrationDto appUserDto,PasswordEncoder passwordEncoder){
        AppUser appUser = new AppUser();
        appUser.setUserEmail(appUserDto.getUserEmail());
        appUser.setUserName(appUserDto.getUserName());
        appUser.setPhoneNumber(appUserDto.getPhoneNumber());
        String encodePassword = passwordEncoder.encode(appUserDto.getPassword());
        appUser.setUserPassword(encodePassword);
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
    public static AppUser appUserDtoSeparator(FacultyRegistrationDto facultyRegistrationDto,PasswordEncoder passwordEncoder){
        AppUser appUser = new AppUser();
        appUser.setUserName(facultyRegistrationDto.getUserName());
        appUser.setUserEmail(facultyRegistrationDto.getUserEmail());
        appUser.setPhoneNumber(facultyRegistrationDto.getPhoneNumber());
        String encodePassword = passwordEncoder.encode(facultyRegistrationDto.getPassword());
        appUser.setUserPassword(encodePassword);
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

    public static LearningMaterial learningMaterialDtoSeparator(LearningMaterialRegistrationDto dto) throws IOException {
        LearningMaterial learningMaterial = new LearningMaterial();
        learningMaterial.setLearningMaterialTitle(dto.getLearningMaterialTitle());

        MultipartFile file = dto.getLearningMaterialFile();
        if (file == null || file.isEmpty()) {
            throw new IOException("File is empty or missing");
        }

        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) uploadDir.mkdirs();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir.getAbsolutePath() + File.separator + fileName);
        file.transferTo(dest);
        learningMaterial.setLearningMaterialFile(dest.getPath());
        learningMaterial.setLearningMaterialUploadedDate(LocalDateTime.now());
        learningMaterial.setLearningMaterialStatus("UPLOADED");

        return learningMaterial;
    }
}
