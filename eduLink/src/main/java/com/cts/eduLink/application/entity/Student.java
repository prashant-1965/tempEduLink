package com.cts.eduLink.application.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private Long studentId;

    private LocalDate studentDOB;
    private String studentGender;
    private String studentAddress;
    private LocalDateTime studentEnrollmentDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id",referencedColumnName = "id")
    private AppUser appUser;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<PerformanceMetric> metricList;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Attendance> attendanceList;

    @OneToMany(mappedBy = "student")
    private List<Grade> gradeList;

    @ManyToMany
    @JoinTable(
            name = "student_course_mapping",
            joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id")
    )
    private Set<Course> courseSet = new HashSet<>();
}
