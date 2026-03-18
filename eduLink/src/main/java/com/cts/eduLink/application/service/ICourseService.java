package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.CourseEnrollmentDto;
import com.cts.eduLink.application.dto.CourseRegistrationDto;
import com.cts.eduLink.application.projection.CourseDetailByIdProjection;
import com.cts.eduLink.application.projection.CourseDetailProjection;

import java.util.List;

public interface ICourseService {
    String registerCourse(CourseRegistrationDto courseRegistrationDto);
    List<CourseDetailProjection> findAllAvailableCourse();
    CourseDetailByIdProjection findCourseDetailsById(Long courseId);
    String courseEnrollmentRequest(CourseEnrollmentDto courseEnrollmentDto);
    String updateCourseRating(Long courseId, double newCourseRating);
    List<CourseDetailProjection> findCourseListByStudentId(Long studentId);

}
