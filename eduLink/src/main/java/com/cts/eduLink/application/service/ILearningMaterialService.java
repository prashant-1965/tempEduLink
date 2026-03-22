package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.LearningMaterialRegistrationDto;
import com.cts.eduLink.application.projection.LearningCourseMaterialProjection;
import org.springframework.core.io.Resource;

public interface ILearningMaterialService {
    String registerLearningMaterial(LearningMaterialRegistrationDto learningMaterialRegistrationDto);
    LearningCourseMaterialProjection findMaterialsByCourseId(Long courseId);
    Resource getFileFromProjection(Long id);
}
