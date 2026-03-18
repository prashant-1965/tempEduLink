package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.StudentRegistrationDto;

public interface IStudentService {
    String registerStudent(StudentRegistrationDto studentRegistrationDto);
    int studentCourseEnrollCount(Long studentId);
}
