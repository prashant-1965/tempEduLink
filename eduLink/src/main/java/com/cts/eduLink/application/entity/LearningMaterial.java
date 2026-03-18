package com.cts.eduLink.application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;

@Entity
@Data
public class LearningMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String learningMaterialTitle;
    private File learningMaterialFile;
    private LocalDateTime learningMaterialUploadedDate;
    private String learningMaterialStatus;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

}
