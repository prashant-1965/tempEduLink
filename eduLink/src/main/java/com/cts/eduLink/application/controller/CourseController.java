package com.cts.eduLink.application.controller;

import com.cts.eduLink.application.dto.CourseEnrollmentDto;
import com.cts.eduLink.application.dto.CourseRegistrationDto;
import com.cts.eduLink.application.projection.CourseDetailByIdProjection;
import com.cts.eduLink.application.projection.CourseDetailProjection;
import com.cts.eduLink.application.service.ICourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
@Slf4j
public class CourseController {

    private final ICourseService iCourseService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCourse(@Valid @RequestBody CourseRegistrationDto courseRegistrationDto){
        log.info("{} request for a new course registration",courseRegistrationDto.getFacultyId());
        return  ResponseEntity.status(200).body(iCourseService.registerCourse(courseRegistrationDto));
    }

    @GetMapping("/findCourseDetailsById/{courseId}")
    public ResponseEntity<CourseDetailByIdProjection> findCourseById(@PathVariable Long courseId){
        log.info("User requested for details of courseId: {} ",courseId);
        return ResponseEntity.status(200).body(iCourseService.findCourseDetailsById(courseId));
    }

    @GetMapping("/findAllAvailableCourse")
    public ResponseEntity<List<CourseDetailProjection>> findALlAvailableCourse(){
        log.info("User has called the endpoint successFully to fetch all available courses");
        return ResponseEntity.status(200).body(iCourseService.findAllAvailableCourse());
    }

    @GetMapping("/allCourseListByStudentId/{studentId}")
    public ResponseEntity<List<CourseDetailProjection>> findCourseListByStudentId(Long studentId){
        return ResponseEntity.status(200).body(iCourseService.findCourseListByStudentId(studentId));
    }

    @PatchMapping("/enrollmentRequest")
    public ResponseEntity<String> courseEnrollmentRequest(@RequestBody CourseEnrollmentDto courseEnrollmentDto){
        return ResponseEntity.status(200).body(iCourseService.courseEnrollmentRequest(courseEnrollmentDto));
    }
    @PatchMapping("/updateRating/{courseId}/{newCourseRating}")
    public ResponseEntity<String> updateCourseRating(@PathVariable Long courseId, @PathVariable double newCourseRating){
        return ResponseEntity.status(200).body(iCourseService.updateCourseRating(courseId,newCourseRating));
    }
}
