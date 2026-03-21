package com.cts.eduLink.application.util;

import com.cts.eduLink.application.projection.CourseAttendanceProjection;
import com.cts.eduLink.application.projection.CourseSummaryProjection;

public class ProjectionMapper {

    public static CourseAttendanceProjection courseAttendanceProjection(CourseSummaryProjection course, Double attendancePercentage){
        CourseAttendanceProjection courseAttendanceProjection = new CourseAttendanceProjection();
        courseAttendanceProjection.setCourseId(course.getCourseId());
        courseAttendanceProjection.setAttendancePercentage(attendancePercentage);
        courseAttendanceProjection.setCourseName(course.getCourseName());
        return courseAttendanceProjection;
    }
}
