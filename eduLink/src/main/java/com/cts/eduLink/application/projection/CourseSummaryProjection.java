package com.cts.eduLink.application.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CourseSummaryProjection {
    private Long id;
    private Long courseId;
    private String courseName;
}
