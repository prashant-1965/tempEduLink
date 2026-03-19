package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.GradeRegistrationDto;
import org.springframework.data.repository.query.Param;

public interface IGradeService {
    String registerGrade(GradeRegistrationDto gradeRegistrationDto);
    String findGradeStatus(Long gradeId);
}
