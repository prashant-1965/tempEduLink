package com.cts.eduLink.application.controller;

import com.cts.eduLink.application.dto.AttendanceRegistrationDto;
import com.cts.eduLink.application.service.IAttendanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
