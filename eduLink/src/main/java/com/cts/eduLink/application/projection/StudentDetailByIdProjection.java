package com.cts.eduLink.application.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class StudentDetailByIdProjection {
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long studentPhoneNumber;
    private LocalDate studentDOB;
    private String studentGender;
    private String studentAddress;
    private LocalDateTime studentEnrollmentDateTime;
}
