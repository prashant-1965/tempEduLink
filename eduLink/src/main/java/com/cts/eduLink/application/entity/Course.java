package com.cts.eduLink.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private Long courseId;

    private String courseTitle;
    private String courseSubject;
    private String courseGradeLevel;
    private int courseCredit;
    private String courseStatus;
    private double courseRating;
    private Long totalCourseRatingCount;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<PerformanceMetric> metrics;

    @ManyToMany(mappedBy = "courseSet")
    private Set<Faculty> facultySet = new HashSet<>();

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Exam> examList;

    @OneToMany(mappedBy = "course")
    private List<LearningMaterial> learningMaterialList;

    @ManyToMany(mappedBy = "courseSet")
    private Set<Student> studentSet = new HashSet<>();

}
