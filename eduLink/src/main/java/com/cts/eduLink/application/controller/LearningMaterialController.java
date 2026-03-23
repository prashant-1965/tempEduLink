package com.cts.eduLink.application.controller;

import com.cts.eduLink.application.dto.LearningMaterialRegistrationDto;
import com.cts.eduLink.application.projection.LearningCourseMaterialProjection;
import com.cts.eduLink.application.service.ILearningMaterialService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/learningMaterial")
public class LearningMaterialController {

    private final ILearningMaterialService learningMaterialService;

    @PreAuthorize("hasRole('FACULTY')")
    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerLearningMaterial(@ModelAttribute LearningMaterialRegistrationDto learningMaterialRegistrationDto){
        log.info("Received POST request to register learning material for Course ID: {}", learningMaterialRegistrationDto.getCourseId());
        return ResponseEntity.status(200).body(learningMaterialService.registerLearningMaterial(learningMaterialRegistrationDto));
    }

    @PreAuthorize("hasRole('STUDENT')")    @GetMapping("/findCourseMaterial/{courseId}")
    public ResponseEntity<LearningCourseMaterialProjection> findLearningCourseMaterialByCourseId(@PathVariable Long courseId){
        return ResponseEntity.status(200).body(learningMaterialService.findMaterialsByCourseId(courseId));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/displayLearningMaterialContent/{id}")
    public ResponseEntity<Resource> displayLearningMaterialContent(@PathVariable Long id){
        return ResponseEntity.status(200).body(learningMaterialService.getFileFromProjection(id));
    }

}
