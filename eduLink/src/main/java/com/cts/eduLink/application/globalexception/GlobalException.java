package com.cts.eduLink.application.globalexception;

import com.cts.eduLink.application.classexception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(CourseException.class)
    public ResponseEntity<String> courseExceptionHandler(CourseException c){
        return ResponseEntity.status(c.getHttpStatus()).body(c.getMessage());
    }

    @ExceptionHandler(AppUserException.class)
    public ResponseEntity<String> appUserExceptionHandler(AppUserException a){
        return ResponseEntity.status(a.getHttpStatus()).body(a.getMessage());
    }

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<String> studentExceptionHandler(StudentException s){
        return ResponseEntity.status(s.getHttpStatus()).body(s.getMessage());
    }

    @ExceptionHandler(FacultyException.class)
    public ResponseEntity<String> facultyExceptionHandler(FacultyException f){
        return ResponseEntity.status(f.getHttpStatus()).body(f.getMessage());
    }

    @ExceptionHandler(ExamException.class)
    public ResponseEntity<String> examExceptionHandler(ExamException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(GradeException.class)
    public ResponseEntity<String> gradeExceptionHandler(GradeException g){
        return ResponseEntity.status(g.getHttpStatus()).body(g.getMessage());
    }
}
