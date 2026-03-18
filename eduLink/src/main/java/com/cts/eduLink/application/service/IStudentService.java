package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.StudentRegistrationDto;
import com.cts.eduLink.application.projection.StudentDetailByIdProjection;

public interface IStudentService {
    String registerStudent(StudentRegistrationDto studentRegistrationDto);
    int studentCourseEnrollCount(Long studentId);
    StudentDetailByIdProjection findStudentDetailsById(Long studentId);

}
