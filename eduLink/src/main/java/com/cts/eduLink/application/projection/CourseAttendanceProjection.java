package com.cts.eduLink.application.projection;

import lombok.Data;

@Data
public class CourseAttendanceProjection {
    private Long courseId;
    private String courseName;
    private double attendancePercentage;
}
