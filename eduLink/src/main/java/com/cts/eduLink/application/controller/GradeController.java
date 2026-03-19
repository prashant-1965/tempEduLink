package com.cts.eduLink.application.controller;

import com.cts.eduLink.application.dto.GradeRegistrationDto;
import com.cts.eduLink.application.service.IGradeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/grade")
public class GradeController {
    private final IGradeService gradeService;

    @PostMapping("/register")
    public ResponseEntity<String> registerGrade(@RequestBody GradeRegistrationDto gradeRegistrationDto){
        return ResponseEntity.status(200).body(gradeService.registerGrade(gradeRegistrationDto));
    }

    @GetMapping("/status/{gradeId}")
    public ResponseEntity<String> getGradeStatusById(@PathVariable Long gradeId){
        return ResponseEntity.status(200).body(gradeService.findGradeStatus(gradeId));
    }
}
