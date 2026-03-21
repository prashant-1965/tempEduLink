package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.AttendanceRegistrationDto;
import com.cts.eduLink.application.projection.CourseAttendanceProjection;

import java.util.List;

public interface IAttendanceService {
    String registerAttendanceByStudentId(AttendanceRegistrationDto attendanceRegistrationDto);
    List<CourseAttendanceProjection> findAttendanceByCourse(Long studentId);
}
