package com.cts.eduLink.application.controller;

import com.cts.eduLink.application.dto.AttendanceRegistrationDto;
import com.cts.eduLink.application.projection.CourseAttendanceProjection;
import com.cts.eduLink.application.service.IAttendanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/attendance")
public class AttendanceController {

    private final IAttendanceService attendanceService;

    @PostMapping("/register")
    public ResponseEntity<String> registerAttendanceByStudentId(@RequestBody AttendanceRegistrationDto attendanceRegistrationDto){
        return ResponseEntity.status(200).body(attendanceService.registerAttendanceByStudentId(attendanceRegistrationDto));
    }

    @GetMapping("/attendanceDetailsByStudentId/{studentId}")
    public ResponseEntity<List<CourseAttendanceProjection>> findAttendanceDetailsByStudentId(@PathVariable Long studentId){
        log.info("REST request to get attendance details for Student ID: {}", studentId);
        return ResponseEntity.status(200).body(attendanceService.findAttendanceByCourse(studentId));
    }
}
