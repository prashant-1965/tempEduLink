package com.cts.eduLink.application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long examId;
    private LocalDateTime examLocalDateTime;
    private String examName;
    private String examStatus;

    @ManyToOne
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;

    @OneToMany(mappedBy = "exam")
    private List<Grade> gradeList;
}
