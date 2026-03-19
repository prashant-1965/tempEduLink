package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.AttendanceRegistrationDto;

public interface IAttendanceService {
    String registerAttendanceByStudentId(AttendanceRegistrationDto attendanceRegistrationDto);
}
