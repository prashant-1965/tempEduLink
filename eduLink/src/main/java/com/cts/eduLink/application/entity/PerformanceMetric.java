package com.cts.eduLink.application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class PerformanceMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private Long metricId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;

    private double score;
    private LocalDate localDate;
    private  String status;
}
