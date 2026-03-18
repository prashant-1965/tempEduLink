package com.cts.eduLink.application.controller;


import com.cts.eduLink.application.dto.ExamRegistrationDto;
import com.cts.eduLink.application.service.IExamService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam")
@AllArgsConstructor
public class ExamController {

    private final IExamService examService;

    @PostMapping("/register")
    public ResponseEntity<String> registerExam(@RequestBody ExamRegistrationDto examRegistrationDto){
        return ResponseEntity.status(200).body(examService.registerExam(examRegistrationDto));
    }
}
