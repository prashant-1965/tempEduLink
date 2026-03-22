package com.cts.eduLink.application.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LearningCourseMaterialProjection {
    private Long id;
    private String learningMaterialTitle;
    private String learningMaterialFile;
    private Long courseId;
}
