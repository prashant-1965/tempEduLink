package com.cts.eduLink.application.controller;

import com.cts.eduLink.application.dto.StudentRegistrationDto;
import com.cts.eduLink.application.projection.CourseDetailProjection;
import com.cts.eduLink.application.projection.StudentDetailByIdProjection;
import com.cts.eduLink.application.service.IStudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
@Slf4j
public class StudentController {

    private final IStudentService iStudentService;

    @PostMapping("/register")
    public ResponseEntity<String> studentRegistration(@Valid @RequestBody StudentRegistrationDto studentRegistrationDto){
        log.info("Student's registration request has been initiated successFully by {}",studentRegistrationDto.getUserName());
        return ResponseEntity.status(200).body(iStudentService.registerStudent(studentRegistrationDto));
    }

    @GetMapping("/enrolledList/{studentId}")
    public ResponseEntity<Integer> findCourseEnrollmentList(@PathVariable Long studentId){
        return ResponseEntity.status(200).body(iStudentService.studentCourseEnrollCount(studentId));
    }
    @GetMapping("/studentDetailsById/{studentId}")
    public ResponseEntity<StudentDetailByIdProjection> studentDetailsById(@PathVariable Long studentId){
        log.info("REST request received to fetch details for Student ID: {}", studentId);
        return ResponseEntity.status(200).body(iStudentService.findStudentDetailsById(studentId));
    }

}
