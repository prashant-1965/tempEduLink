package com.cts.eduLink.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LearningMaterialRegistrationDto {
    private String learningMaterialTitle;
    private MultipartFile learningMaterialFile;
    private Long courseId;
}
